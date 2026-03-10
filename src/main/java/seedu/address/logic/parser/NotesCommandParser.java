package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.NotesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Notes;

/**
 * Parses input arguments and creates a new {@code NotesCommand} object
 */
public class NotesCommandParser implements Parser<NotesCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code NotesCommand}
     * and returns a {@code NotesCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NotesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] notesArgs = args.trim().split(" ", 2);

        Index index;
        String notes;
        try {
            index = ParserUtil.parseIndex(notesArgs[0]);
            notes = notesArgs[1];
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE), ive);
        } catch (ArrayIndexOutOfBoundsException ae) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE), ae);
        }

        return new NotesCommand(index, new Notes(notes));
    }
}
