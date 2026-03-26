package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

/**
 * Parent class for all view-related commands, which opens the view panel.
 */
public abstract class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the detailed information of the contact identified by the index number "
            + "used in the displayed contact list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Or: " + COMMAND_WORD + " " + PREFIX_FILE + " to view the list of all B2B4U data files in use.";
}
