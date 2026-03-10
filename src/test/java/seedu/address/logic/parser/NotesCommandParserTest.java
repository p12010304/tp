package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NotesCommand;

public class NotesCommandParserTest {
    private static final String NOTES_STRING = "To follow up on Wednesday";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotesCommand.MESSAGE_USAGE);

    private NotesCommandParser parser = new NotesCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, NOTES_STRING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NOTES_STRING, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NOTES_STRING, MESSAGE_INVALID_FORMAT);
    }
}
