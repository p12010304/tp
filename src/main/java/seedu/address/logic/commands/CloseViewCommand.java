package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Closes the contact detail view panel.
 */
public class CloseViewCommand extends Command {

    public static final String COMMAND_WORD = "close";
    public static final String COMMAND_SUBWORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_SUBWORD
            + ": Closes the contact detail view panel.";

    public static final String MESSAGE_SUCCESS = "View panel closed.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, false, false, null, true);
    }
}
