package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NoteAddCommand;
import seedu.address.logic.commands.NoteClearCommand;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.NoteRemoveCommand;
import seedu.address.model.contact.Note;

public class NoteCommandParserTest {
    private static final String NOTES_STRING = "To follow up on Wednesday";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);
    private static final String NUM_LINES_STRING = "1";
    private static final int NUM_LINES = 1;

    private NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parse_noteAddCommand_success() {
        NoteAddCommand expectedNoteAddCommand =
            new NoteAddCommand(INDEX_FIRST_CONTACT, new Note(NOTES_STRING));
        assertParseSuccess(
            parser,
            "1 " + NOTES_STRING,
            expectedNoteAddCommand);
    }

    @Test
    public void parse_stringReminderNoteAddCommand_success() {
        NoteAddCommand expectedNoteAddCommand =
                new NoteAddCommand(INDEX_FIRST_CONTACT, new Note(NOTES_STRING));
        assertParseSuccess(
                parser,
                "1 " + NOTES_STRING + " " + PREFIX_ON + "timeString",
                expectedNoteAddCommand);
    }

    @Test
    public void parse_dateReminderNoteAddCommand_success() {
        NoteAddCommand expectedNoteAddCommand =
                new NoteAddCommand(INDEX_FIRST_CONTACT, new Note(NOTES_STRING));
        assertParseSuccess(
                parser,
                "1 " + NOTES_STRING + " " + PREFIX_ON + "March 11",
                expectedNoteAddCommand);
    }

    @Test
    public void parse_datetimeReminderNoteAddCommand_success() {
        NoteAddCommand expectedNoteAddCommand =
                new NoteAddCommand(INDEX_FIRST_CONTACT, new Note(NOTES_STRING));
        assertParseSuccess(
                parser,
                "1 " + NOTES_STRING + " " + PREFIX_ON + "March 11 2025 13:30",
                expectedNoteAddCommand);
    }

    @Test
    public void parse_noteRemoveCommand_success() {
        NoteRemoveCommand expectedNoteRemoveCommand =
            new NoteRemoveCommand(INDEX_FIRST_CONTACT, NUM_LINES);
        assertParseSuccess(
            parser,
            "1 " + PREFIX_REMOVE + NUM_LINES_STRING,
            expectedNoteRemoveCommand);
    }

    @Test
    public void parse_noteClearCommand_success() {
        NoteClearCommand expectedNoteClearCommand =
            new NoteClearCommand(INDEX_FIRST_CONTACT);
        assertParseSuccess(
            parser,
            "1 " + PREFIX_CLEAR + NOTES_STRING,
            expectedNoteClearCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, NOTES_STRING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // remove command with no index specified
        assertParseFailure(parser, PREFIX_REMOVE + "1", MESSAGE_INVALID_FORMAT);

        // remove command with no number of lines specified
        assertParseFailure(parser, "1 " + PREFIX_REMOVE, MESSAGE_INVALID_FORMAT);

        // remove command with no index and no number of lines specified
        assertParseFailure(parser, PREFIX_REMOVE.toString(), MESSAGE_INVALID_FORMAT);

        // clear command with no index specified
        assertParseFailure(parser, PREFIX_CLEAR.toString(), MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NOTES_STRING, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NOTES_STRING, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_tooManyArguments_failure() {
        // add + remove
        assertParseFailure(
            parser,
            "1 " + NOTES_STRING + " " + PREFIX_REMOVE + "1",
            MESSAGE_INVALID_FORMAT);

        // add + clear
        assertParseFailure(
            parser,
            "1 " + NOTES_STRING + " " + PREFIX_CLEAR,
            MESSAGE_INVALID_FORMAT);

        // remove + clear
        assertParseFailure(
            parser,
            "1 " + PREFIX_REMOVE + "1 " + PREFIX_CLEAR,
            MESSAGE_INVALID_FORMAT);

        // add + remove + clear
        assertParseFailure(
            parser,
            "1 " + NOTES_STRING + " " + PREFIX_REMOVE + "1 " + PREFIX_CLEAR,
            MESSAGE_INVALID_FORMAT);
    }
}
