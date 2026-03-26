package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SetAddressBookFilePathCommandTest {
    private static final String FILE_NAME = "new_book";

    @Test
    public void executeTest() throws CommandException {
        Model model = new ModelManager();
        SetAddressBookFilePathCommand setCommand = new SetAddressBookFilePathCommand("new_book");
        CommandResult commandResult = setCommand.execute(model);
        assertEquals(UserPrefs.formatAddressBookFilePath("new_book"), model.getAddressBookFilePath());
        assertEquals(
                String.format(SetAddressBookFilePathCommand.MESSAGE_SUCCESS,
                        UserPrefs.formatAddressBookFilePath("new_book")),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        final SetAddressBookFilePathCommand standardCommand = new SetAddressBookFilePathCommand(FILE_NAME);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new SetAddressBookFilePathCommand("NEWBOOK")));
    }
}
