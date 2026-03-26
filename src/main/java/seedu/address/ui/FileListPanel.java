package seedu.address.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.JsonAddressBookStorage;

/**
 * Panel containing the list of B2B4U.
 */
public class FileListPanel extends UiPart<Region> {
    private static final String FXML = "FileListPanel.fxml";
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    private final WatchService watchService;
    private final ObservableList<FileCardDetails> fileDetailList = FXCollections.observableArrayList();
    private final Logger logger = LogsCenter.getLogger(ContactListPanel.class);

    @FXML
    private ListView<FileCardDetails> fileListView;

    /**
     * Creates a {@code FileListPanel} with the given directory.
     */
    public FileListPanel(Path directory) throws IOException {
        super(FXML);
        File[] existingFiles = directory.toFile().listFiles();
        if (existingFiles != null) {
            for (File file : existingFiles) {
                try {
                    fileDetailList.add(getFileCardDetails(file));
                } catch (Exception e) {
                    logger.warning(e.getMessage());
                }
            }
        }

        watchService = FileSystems.getDefault().newWatchService();
        setUpWatcher(directory);

        SortedList<FileCardDetails> sortedFileDetails = new SortedList<>(fileDetailList,
                Comparator.comparing(FileCardDetails::lastModifiedTime, Comparator.reverseOrder()));
        fileListView.setItems(sortedFileDetails);
        fileListView.setCellFactory(listView -> new FileListPanel.FileListViewCell());

        // Refresh all visible cells when any file changes
        fileDetailList.addListener((ListChangeListener<FileCardDetails>) change -> {
            fileListView.refresh();
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code File} using a {@code FileCard}.
     */
    class FileListViewCell extends ListCell<FileCardDetails> {
        @Override
        protected void updateItem(FileCardDetails details, boolean empty) {
            super.updateItem(details, empty);

            if (empty || details == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FileCard(details).getRoot());
            }
        }
    }

    public static FileCardDetails getFileCardDetails(File file) throws Exception {
        int contactCount;
        Path filePath = file.toPath();
        try {
            Optional<ReadOnlyAddressBook> addressBookOptional = new JsonAddressBookStorage(filePath).readAddressBook();
            if (!addressBookOptional.isPresent()) {
                throw new Exception("AddressBook file " + filePath + " not found");
            }
            contactCount = addressBookOptional.get().getContactList().size();
        } catch (DataLoadingException e) {
            throw e;
        }

        return new FileCardDetails(file.getName(), epochToTime(file.lastModified()), contactCount);
    }

    public static LocalDateTime epochToTime(long epoch) {
        return Instant.ofEpochMilli(epoch).atZone(ZONE_ID).toLocalDateTime();
    }

    private void setUpWatcher(Path directory) throws IOException {
        directory.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);
        EXECUTOR_SERVICE.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    WatchKey key = watchService.take();

                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        Path alteredPath = directory.resolve((Path) event.context());
                        try {
                            Platform.runLater(() -> {
                                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                                    try {
                                        fileDetailList.add(getFileCardDetails(alteredPath.toFile()));
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                                    fileDetailList.removeIf(f -> f.name().equals(alteredPath.toFile().getName()));
                                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                                    FileCardDetails change;
                                    try {
                                        change = getFileCardDetails(alteredPath.toFile());
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                    int index = fileDetailList.indexOf(change);
                                    if (index >= 0) {
                                        fileDetailList.set(index, change);
                                    }
                                }
                            });
                        } catch (Exception e) {
                            logger.warning(e.getMessage());
                        }
                    }

                    // directory no longer accessible
                    if (!key.reset()) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
