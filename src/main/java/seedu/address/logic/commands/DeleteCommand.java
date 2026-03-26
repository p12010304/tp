package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

/**
 * Parent class for all delete-related commands.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the contact identified by the index number used in the displayed contact list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1"
            + "Or: " + COMMAND_WORD + " " + PREFIX_FILE + "FILE_NAME to delete a B2B4U data file in use.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_FILE + "file_to_delete";
}
