package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SetAddressBookFilePathCommand;
import seedu.address.logic.commands.SetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SetCommandParserTest {
    private static final String FILENAME = "new_book";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE);
    private static final String MESSAGE_INVALID_FILENAME =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAddressBookFilePathCommand.MESSAGE_CONSTRAINTS);

    private SetCommandParser parser = new SetCommandParser();

    @Test
    public void parse_setAddressBookFilePathCommand_success() {
        SetAddressBookFilePathCommand expectedSetAddressBookFilePathCommand =
                new SetAddressBookFilePathCommand(FILENAME);
        assertParseSuccess(
                parser,
                " " + PREFIX_FILE + FILENAME,
                expectedSetAddressBookFilePathCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, PREFIX_FILE.toString(), MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // clear command with no index specified
        assertParseFailure(parser, " " + PREFIX_FILE + "&newbook", MESSAGE_INVALID_FILENAME);

        assertThrows(ParseException.class, () -> parser.parse(PREFIX_FILE + "&newbook"));
    }
}
