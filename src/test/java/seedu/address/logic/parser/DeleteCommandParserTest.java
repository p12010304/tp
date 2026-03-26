package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteContactCommand;
import seedu.address.logic.commands.DeleteFileCommand;
import seedu.address.model.UserPrefs;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteContactCommand() {
        assertParseSuccess(parser, " 1", new DeleteContactCommand(INDEX_FIRST_CONTACT));
    }

    @Test
    public void parse_validArgs_returnsDeleteFileCommand() {
        assertParseSuccess(parser, " file/newbook", new DeleteFileCommand(Paths.get("data", "newbook.json")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", Messages.getCommandErrorWithUsage(ParserUtil.MESSAGE_INVALID_INDEX,
                DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidFileName_throwsParseException() {
        assertParseFailure(parser, " file/&newbook",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UserPrefs.FILENAME_CONSTRAINTS_MESSAGE));
    }
}
