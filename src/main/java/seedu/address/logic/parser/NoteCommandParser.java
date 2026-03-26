package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.NoteAddCommand;
import seedu.address.logic.commands.NoteClearAllCommand;
import seedu.address.logic.commands.NoteClearCommand;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.NoteRemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Note;

/**
 * Parses input arguments and creates a new {@code NoteCommand} object
 */
public class NoteCommandParser implements Parser<NoteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code NotesCommand}
     * and returns a {@code NotesCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public NoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ON, PREFIX_CLEAR, PREFIX_CLEAR_ALL, PREFIX_REMOVE);

        boolean isClearPrefixPresent = argMultimap.getValue(PREFIX_CLEAR).isPresent();
        boolean isClearAllPrefixPresent = argMultimap.getValue(PREFIX_CLEAR_ALL).isPresent();
        boolean isRemovePrefixPresent = argMultimap.getValue(PREFIX_REMOVE).isPresent();
        boolean isPreamblePresent = !argMultimap.getPreamble().isEmpty();

        if (!isPreamblePresent) {
            throw new ParseException(Messages.getCommandErrorWithUsage(
                    Messages.MESSAGE_MISSING_INDEX, NoteAddCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getArguments().size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE));
        }

        if (isClearPrefixPresent) {
            return parseNoteClearCommand(argMultimap);
        }

        if (isClearAllPrefixPresent) {
            return parseNoteClearAllCommand(argMultimap);
        }

        if (isRemovePrefixPresent) {
            return parseNoteRemoveCommand(argMultimap);
        }

        return parseNoteAddCommand(argMultimap);
    }

    /**
     * Parses the given {@code ArgumentMultimap} in the context of the {@code NoteClearCommand}
     * and returns a {@code NoteClearCommand} object for execution.
     *
     * @param argMultimap The ArgumentMultimap containing an {@code Index} preamble
     *                    and a prefixed number of lines to remove.
     * @return A {@code NoteClearCommand} object with the specified index and number of lines to remove.
     */
    private NoteClearCommand parseNoteClearCommand(ArgumentMultimap argMultimap) throws ParseException {
        Index index = parseIndex(argMultimap.getPreamble());
        int numLines = parseNumLines(argMultimap.getValue(PREFIX_CLEAR).get());
        return new NoteClearCommand(index, numLines);
    }

    /**
     * Parses the given {@code ArgumentMultimap} in the context of the {@code NoteClearCommand}
     * and returns a {@code NoteClearCommand} object for execution.
     *
     * @param argMultimap The ArgumentMultimap containing an {@code Index} preamble.
     * @return A {@code NoteClearCommand} object with the specified index.
     */
    private NoteClearAllCommand parseNoteClearAllCommand(ArgumentMultimap argMultimap) throws ParseException {
        Index index = parseIndex(argMultimap.getPreamble());
        return new NoteClearAllCommand(index);
    }

    /**
     * Parses the given {@code ArgumentMultimap} in the context of the {@code NoteRemoveCommand}
     * and returns a {@code NoteRemoveCommand} object for execution.
     *
     * @param argMultimap The ArgumentMultimap containing an {@code Index} preamble
     *                    and a prefixed note {@code Index} to remove.
     * @return A {@code NoteRemoveCommand} object with the specified index and note index to remove.
     */
    private NoteRemoveCommand parseNoteRemoveCommand(ArgumentMultimap argMultimap) throws ParseException {
        Index contactIndex = parseIndex(argMultimap.getPreamble());
        Index noteIndex = parseIndex(argMultimap.getValue(PREFIX_REMOVE).get());
        return new NoteRemoveCommand(contactIndex, noteIndex);
    }

    /**
     * Parses the given {@code String} in the context of the {@code NoteAddCommand}
     * and returns a {@code NoteAddCommand} object for execution.
     *
     * @param argMultimap The ArgumentMultimap containing an {@code Index} preamble.
     * @return A {@code NoteAddCommand} object with the specified index, a new note, and potentially a time.
     */
    private NoteAddCommand parseNoteAddCommand(ArgumentMultimap argMultimap) throws ParseException {
        String[] noteArgs = argMultimap.getPreamble().trim().split(" ", 2);

        if (noteArgs.length < 2) {
            throw new ParseException(Messages.getCommandErrorWithUsage(
                    Messages.MESSAGE_MISSING_KEYWORD, NoteAddCommand.MESSAGE_USAGE),
                new ArrayIndexOutOfBoundsException());
        }

        Index index = parseIndex(noteArgs[0]);

        if (argMultimap.getValue(PREFIX_ON).isPresent()) {
            return new NoteAddCommand(
                    index,
                    new Note(noteArgs[1], TimePointParser.toTimePoint(argMultimap.getValue(PREFIX_ON).get())));
        }

        return new NoteAddCommand(index, new Note(noteArgs[1]));
    }

    /**
     * Parses a {@code String} into an {@code Index}.
     *
     * @param index A {@code String} index.
     * @return A parsed {@code Index}.
     */
    private Index parseIndex(String index) throws ParseException {
        try {
            return ParserUtil.parseIndex(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(Messages.getCommandErrorWithUsage(
                    ive.getMessage(), NoteAddCommand.MESSAGE_USAGE), ive);
        }
    }

    /**
     * Parses a {@code String} into an {@code int} number of lines.
     *
     * @param numLines A {@code String} number of lines.
     * @return A parsed {@code int} number of lines.
     */
    private int parseNumLines(String numLines) throws ParseException {
        try {
            return Integer.parseInt(numLines);
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE), nfe);
        }
    }
}
