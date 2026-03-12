package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.NoteCommandParser.PREFIX_CLEAR;
import static seedu.address.logic.parser.NoteCommandParser.PREFIX_REMOVE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NoteCommand;

public class NoteCommandParserTest {
    private static final String NOTES_STRING = "To follow up on Wednesday";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);

    private NoteCommandParser parser = new NoteCommandParser();

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
