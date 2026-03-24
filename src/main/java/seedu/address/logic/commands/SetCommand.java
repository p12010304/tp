package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

/**
 * Parent class for all setting-related commands which alter {@code UserPrefs}.
 */
public abstract class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes settings.\n"
            + "Parameters: "
            + "[" + PREFIX_FILE + "FILE_NAME] "
            + "Example: " + COMMAND_WORD + PREFIX_FILE + "contacts";
}
