package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Restores the model to a snapshot before an undo command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Moves the model forward by one command.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_REDO_SUCCESS = "Redo command: \n%1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            String undoneCommand = model.moveSnapshot(1);
            return new CommandResult(String.format(MESSAGE_REDO_SUCCESS, undoneCommand));
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
    }
}
