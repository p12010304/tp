package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewContactCommand;
import seedu.address.logic.commands.ViewFilesCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ViewCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the ViewCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validArgs_returnsViewContactCommand() {
        assertParseSuccess(parser, "1", new ViewContactCommand(INDEX_FIRST_CONTACT));
    }

    @Test
    public void parse_validArgs_returnsViewFilesCommand() {
        assertParseSuccess(parser, " " + PREFIX_FILE, new ViewFilesCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", Messages.getCommandErrorWithUsage(ParserUtil.MESSAGE_INVALID_INDEX,
                ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", Messages.getCommandErrorWithUsage(Messages.MESSAGE_MISSING_INDEX,
                ViewCommand.MESSAGE_USAGE));
    }
}
