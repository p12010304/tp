package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteContactCommand;
import seedu.address.logic.commands.DeleteFileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        if (args == null || args.trim().isEmpty()) {
            throw new ParseException(Messages.getCommandErrorWithUsage(
                    Messages.MESSAGE_MISSING_INDEX, DeleteCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE);
        if (argMultimap.getValue(PREFIX_FILE).isPresent() && argMultimap.getPreamble().isEmpty()) {
            String fileName = argMultimap.getValue(PREFIX_FILE).get();
            if (!UserPrefs.isValidFileName(fileName)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, UserPrefs.FILENAME_CONSTRAINTS_MESSAGE));
            }
            return new DeleteFileCommand(UserPrefs.formatAddressBookFilePath(fileName));
        }

        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteContactCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(Messages.getCommandErrorWithUsage(
                    pe.getMessage(), DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
