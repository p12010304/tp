package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Views a list of all B2B4U data files.
 */
public class ViewFilesCommand extends ViewCommand {

    public static final String MESSAGE_SUCCESS = "File list retrieved.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new ViewFilesCommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ViewFilesCommand;
    }
}
