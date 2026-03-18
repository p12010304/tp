package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CloseViewCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_closeView_success() {
        CommandResult expectedResult = new CommandResult(CloseViewCommand.MESSAGE_SUCCESS,
                false, false, null, true);
        assertCommandSuccess(new CloseViewCommand(), model, expectedResult, expectedModel);
    }

    @Test
    public void execute_resultHidesContactDetail() {
        CloseViewCommand command = new CloseViewCommand();
        CommandResult result = command.execute(model);
        assertTrue(result.isHideContactDetail());
        assertFalse(result.isShowContactDetail());
        assertFalse(result.isShowHelp());
        assertFalse(result.isExit());
    }

    @Test
    public void equals_test() {
        CloseViewCommand command = new CloseViewCommand();

        // same object -> returns true
        assertTrue(command.equals(command));

        // null -> returns false
        assertFalse(command.equals(null));

        // different type -> returns false
        assertFalse(command.equals(1));
    }
}
