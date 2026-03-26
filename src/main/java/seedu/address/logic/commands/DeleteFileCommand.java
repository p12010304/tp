package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import javafx.scene.control.ButtonType;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.ui.DeleteFileAlert;

/**
 * Deletes a given B2B4U data file.
 */
public class DeleteFileCommand extends DeleteCommand {
    public static final String MESSAGE_SUCCESS = "%1$s has been deleted.";
    public static final String MESSAGE_FAILURE_IS_CURRENT_FILE = "Cannot delete file currently being accessed.";
    public static final String MESSAGE_FAILURE_FILE_NOT_FOUND = "File not found.";
    public static final String MESSAGE_FAILURE_INVALID_FILE = "File is not a valid B2B4U data file.";
    public static final String MESSAGE_FAILURE_ABORT = "File deletion aborted.";
    public static final String MESSAGE_FAILURE = "Failed to delete file.";

    private final Path filePath;

    /**
     * @param filePath Path of file to be deleted, without ".json" file format name.
     */
    public DeleteFileCommand(Path filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getAddressBookFilePath().equals(filePath)) {
            throw new CommandException(MESSAGE_FAILURE_IS_CURRENT_FILE);
        }
        if (!Files.exists(filePath)) {
            throw new CommandException(MESSAGE_FAILURE_FILE_NOT_FOUND);
        }

        try {
            Optional<ReadOnlyAddressBook> addressBookOptional = new JsonAddressBookStorage(filePath).readAddressBook();
            if (!addressBookOptional.isPresent()) {
                throw new CommandException(MESSAGE_FAILURE_FILE_NOT_FOUND);
            }
            if (!addressBookOptional.get().getContactList().isEmpty()) {
                DeleteFileAlert alert =
                        new DeleteFileAlert(filePath.getFileName().toString(),
                                addressBookOptional.get().getContactList().size());
                alert.show();

                if (!(alert.getResult() == ButtonType.YES)) {
                    throw new CommandException(MESSAGE_FAILURE_ABORT);
                }
            }
        } catch (DataLoadingException e) {
            throw new CommandException(MESSAGE_FAILURE_INVALID_FILE);
        }

        try {
            Files.delete(filePath);
            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath.getFileName()));
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteFileCommand)) {
            return false;
        }

        DeleteFileCommand otherDeleteFileCommand = (DeleteFileCommand) other;
        return filePath.equals(otherDeleteFileCommand.filePath);
    }
}
