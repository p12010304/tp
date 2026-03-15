package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ReminderAddCommand;
import seedu.address.logic.commands.ReminderClearCommand;
import seedu.address.logic.commands.ReminderCommand;
import seedu.address.logic.commands.ReminderRemoveCommand;
import seedu.address.model.contact.Reminder;
import seedu.address.model.contact.TimePoint;

public class ReminderCommandParserTest {
    private static final String NOTES_STRING = "To follow up";
    private static final String TIME_STRING = "17 jun 2026 13:30";
    private static final TimePoint TIME_POINT = TimePoint.of(LocalDateTime.of(2026, 6, 17, 13, 30));
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE);
    private static final String NUM_REMINDERS_STRING = "1";
    private static final int NUM_REMINDERS = 1;

    private ReminderCommandParser parser = new ReminderCommandParser();

    @Test
    public void parse_reminderAddCommand_success() {
        ReminderAddCommand expectedReminderAddCommand =
                new ReminderAddCommand(INDEX_FIRST_CONTACT, new Reminder(NOTES_STRING, TIME_POINT));
        assertParseSuccess(
                parser,
                "1 " + NOTES_STRING + " " + PREFIX_ON + TIME_STRING,
                expectedReminderAddCommand);
    }

    @Test
    public void parse_reminderRemoveCommand_success() {
        ReminderRemoveCommand expectedReminderRemoveCommand =
                new ReminderRemoveCommand(INDEX_FIRST_CONTACT, NUM_REMINDERS);
        assertParseSuccess(
                parser,
                "1 " + PREFIX_REMOVE + NUM_REMINDERS_STRING,
                expectedReminderRemoveCommand);
    }

    @Test
    public void parse_reminderClearCommand_success() {
        ReminderClearCommand expectedReminderClearCommand =
                new ReminderClearCommand(INDEX_FIRST_CONTACT);
        assertParseSuccess(
                parser,
                "1 " + PREFIX_CLEAR,
                expectedReminderClearCommand);
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
