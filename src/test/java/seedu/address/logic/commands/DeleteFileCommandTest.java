package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class DeleteFileCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void deleteNonExistentFile_throwsException() {
        DeleteCommand deleteCommand = new DeleteFileCommand(Paths.get("dummy.txt"));

        assertCommandFailure(deleteCommand, model, DeleteFileCommand.MESSAGE_FAILURE_FILE_NOT_FOUND);
    }

    @Test
    public void deleteCurrentFile_throwsException() {
        DeleteCommand deleteCommand = new DeleteFileCommand(model.getAddressBookFilePath());

        assertCommandFailure(deleteCommand, model, DeleteFileCommand.MESSAGE_FAILURE_IS_CURRENT_FILE);
    }

    @Test
    public void deleteValidEmptyFile_success() throws IOException {
        Path emptyFilePath =
                Paths.get("src", "test", "data", "JsonSerializableAddressBookTest", "emptyContactAddressBook.json");
        Path filePath =
                Paths.get("src", "test", "data",
                        "JsonSerializableAddressBookTest", "emptyContactAddressBookDuplicate.json");
        Files.copy(emptyFilePath, filePath);
        DeleteCommand deleteCommand = new DeleteFileCommand(filePath);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertCommandSuccess(
                deleteCommand, expectedModel,
                String.format(DeleteFileCommand.MESSAGE_SUCCESS, filePath.getFileName()),
                model);
    }

    @Test
    public void equals() {
        DeleteCommand deleteCommand = new DeleteFileCommand(Paths.get("dummy.txt"));

        // same object -> returns true
        assertTrue(deleteCommand.equals(deleteCommand));

        // null -> returns false
        assertFalse(deleteCommand.equals(null));

        // different types -> returns false
        assertFalse(deleteCommand.equals(new ClearCommand()));

        // same path -> returns true
        assertTrue(deleteCommand.equals(new DeleteFileCommand(Paths.get("dummy.txt"))));

        // different path -> returns false
        assertFalse(deleteCommand.equals(new DeleteFileCommand(Paths.get("temp.txt"))));
    }
}
