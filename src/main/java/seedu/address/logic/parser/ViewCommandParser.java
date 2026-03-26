package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewContactCommand;
import seedu.address.logic.commands.ViewFilesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        assert args != null;
        if (args.isBlank()) {
            throw new ParseException(Messages.getCommandErrorWithUsage(
                    Messages.MESSAGE_MISSING_INDEX, ViewCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE);
        if (argMultimap.getPreamble().isEmpty()
                && argMultimap.getValue(PREFIX_FILE).isPresent()
                && argMultimap.getValue(PREFIX_FILE).get().isEmpty()) {
            return new ViewFilesCommand();
        }
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewContactCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(Messages.getCommandErrorWithUsage(
                    pe.getMessage(), ViewCommand.MESSAGE_USAGE), pe);
        }
    }

}
