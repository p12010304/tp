package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the model to a previous snapshot.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reverts the model back by one command.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_SUCCESS = "Undo command: \n%1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            String undoneCommand = model.moveSnapshot(-1);
            return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, undoneCommand));
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
    }
}
