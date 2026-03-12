package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddNoteCommand;
import seedu.address.logic.commands.ClearNotesCommand;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.RemoveNotesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Note;

/**
 * Parses input arguments and creates a new {@code NotesCommand} object
 */
public class NoteCommandParser implements Parser<NoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code NotesCommand}
     * and returns a {@code NotesCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] noteArgs = args.trim().split(" ", 2);

        if (noteArgs.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE),
                    new ArrayIndexOutOfBoundsException());
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(noteArgs[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE), ive);
        }

        String notes = noteArgs[1];

        if (noteArgs[1].equals("ca/")) {
            return new ClearNotesCommand(index);
        }

        if (notes.startsWith("c/")) {
            return parseRemoveNotesCommand(index, noteArgs[1]);
        }

        return new AddNoteCommand(index, new Note(notes));
    }

    /**
     * Parses the given {@code String} in the context of the {@code RemoveNotesCommand}
     * and returns a {@code RemoveNotesCommand} object for execution.
     * @param index Previously parsed index of the contact to remove notes from.
     * @param args The remaining arguments after parsing the index, expected to be in the format "c/NUM_LINES_TO_REMOVE".
     * @return A RemoveNotesCommand object with the specified index and number of lines to remove.
     */
    private RemoveNotesCommand parseRemoveNotesCommand(Index index, String args) throws ParseException {
        try {
            return new RemoveNotesCommand(index, Integer.parseInt(args.substring(2)));
        } catch (NumberFormatException err) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE), err);
        }
    }
}
