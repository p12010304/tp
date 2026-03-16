package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.Snapshot;
import seedu.address.model.contact.Contact;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    public static final String UNDO_LIMIT_MESSAGE = "Model reverted to earliest snapshot.";
    public static final String REDO_LIMIT_MESSAGE = "Model reverted to newest snapshot.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    private final List<Pair<String, Snapshot>> snapshots;

    private int snapshotPosition;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
        snapshots = new ArrayList<>();
        snapshots.add(new Pair<String, Snapshot>("", model.getSnapshot()));
        snapshotPosition = 0;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        if (commandResult.getSnapshotSteps() == 0) {
            snapshotPosition++;
            if (snapshotPosition < snapshots.size()) {
                snapshots.subList(snapshotPosition, snapshots.size()).clear();
            }
            snapshots.add(
                    snapshotPosition,
                    new Pair<String, Snapshot>(commandResult.getFeedbackToUser(), model.getSnapshot()));
        } else if (commandResult.getSnapshotSteps() > 0) {
            int snapshotsToShift = Math.min(snapshots.size() - snapshotPosition - 1, commandResult.getSnapshotSteps());
            if (snapshotsToShift > 0) {
                snapshotPosition += snapshotsToShift;
                commandResult = new CommandResult(
                        commandResult.getFeedbackToUser() + snapshots.get(snapshotPosition).getKey(),
                        snapshotsToShift);
                model.copySnapshot(snapshots.get(snapshotPosition).getValue());
            } else {
                commandResult = new CommandResult(REDO_LIMIT_MESSAGE);
            }
        } else if (commandResult.getSnapshotSteps() < 0) {
            int snapshotsToShift = Math.min(snapshotPosition, -commandResult.getSnapshotSteps());
            if (snapshotsToShift > 0) {
                snapshotPosition -= snapshotsToShift;
                commandResult = new CommandResult(
                        commandResult.getFeedbackToUser() + snapshots.get(snapshotPosition + 1).getKey(),
                        -snapshotsToShift);
                model.copySnapshot(snapshots.get(snapshotPosition).getValue());
            } else {
                commandResult = new CommandResult(UNDO_LIMIT_MESSAGE);
            }
        }

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return model.getFilteredContactList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
