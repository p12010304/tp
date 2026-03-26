package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AFTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEFORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_CONTACTED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BENSON;
import static seedu.address.testutil.TypicalContacts.CARL;
import static seedu.address.testutil.TypicalContacts.DANIEL;
import static seedu.address.testutil.TypicalContacts.ELLE;
import static seedu.address.testutil.TypicalContacts.FIONA;
import static seedu.address.testutil.TypicalContacts.GEORGE;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindAssociationsCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.util.ContactPredicateBuilder;
import seedu.address.model.timepoint.TimePoint;
import seedu.address.testutil.TypicalIndexes;

public class FindCommandParserTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_success() {
        assertDoesNotThrow(() -> parser.parse("     "));
    }

    @Test
    public void parse_findKeyword_test() throws ParseException {
        FindCommand command = parser.parse("ne");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 4);
        Predicate<Contact> predicate = (Contact contact) -> contact.contains("ne");
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, ELLE, ALICE, BENSON), model.getDisplayedContactList());
    }

    @Test
    public void parse_findName_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_NAME + "Elle");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 1);
        Predicate<Contact> predicate = (Contact contact) -> contact.containsInName("Elle");
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE), model.getDisplayedContactList());
    }

    @Test
    public void parse_findPhone_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_PHONE + "94");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 4);
        Predicate<Contact> predicate = (Contact contact) -> contact.containsInPhone("94");
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GEORGE, FIONA, ELLE, ALICE), model.getDisplayedContactList());
    }

    @Test
    public void parse_findEmail_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_EMAIL + "ANNA");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 1);
        Predicate<Contact> predicate = (Contact contact) -> contact.containsInEmail("ANNA");
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GEORGE), model.getDisplayedContactList());
    }

    @Test
    public void parse_findAddress_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_ADDRESS + "street");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 3);
        Predicate<Contact> predicate = (Contact contact) -> contact.containsInAddress("street");
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, GEORGE, DANIEL), model.getDisplayedContactList());
    }

    @Test
    public void parse_findTags_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_TAG + "friends");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 3);
        Predicate<Contact> predicate = (Contact contact) -> contact.hasTag("friends");
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, ALICE, BENSON), model.getDisplayedContactList());
    }

    @Test
    public void parse_findLastContacted_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_LAST_CONTACTED);
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 5);
        Predicate<Contact> predicate = Contact::hasLastContacted;
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, GEORGE, FIONA, DANIEL, ELLE), model.getDisplayedContactList());
    }

    @Test
    public void parse_findLastContactedOnDay_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_LAST_CONTACTED + PREFIX_ON + "22/02/2026");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 2);
        Predicate<Contact> predicate =
                contact -> contact.lastContactedIsSameDayAs(TimePoint.of(LocalDate.of(2026, 2, 22)));
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GEORGE, FIONA), model.getDisplayedContactList());
    }

    @Test
    public void parse_findLastContactedBeforeDate_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_LAST_CONTACTED + PREFIX_BEFORE + "22/02/2026");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 1);
        Predicate<Contact> predicate =
                contact -> contact.lastContactedIsBefore(TimePoint.of(LocalDate.of(2026, 2, 22)));
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL), model.getDisplayedContactList());
    }

    @Test
    public void parse_findLastContactedBeforeDateTime_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_LAST_CONTACTED + PREFIX_BEFORE + "22/02/2026 14:00");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 2);
        Predicate<Contact> predicate =
                contact -> contact.lastContactedIsBefore(TimePoint.of(LocalDateTime.of(2026, 2, 22, 14, 0)));
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA, DANIEL), model.getDisplayedContactList());
    }

    @Test
    public void parse_findLastContactedAfterDate_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_LAST_CONTACTED + PREFIX_AFTER + "22/02/2026");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 1);
        Predicate<Contact> predicate =
                contact -> contact.lastContactedIsAfter(TimePoint.of(LocalDate.of(2026, 2, 22)));
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getDisplayedContactList());
    }

    @Test
    public void parse_findLastContactedAfterDateTime_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_LAST_CONTACTED + PREFIX_AFTER + "22/02/2026 14:00");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 2);
        Predicate<Contact> predicate =
                contact -> contact.lastContactedIsAfter(TimePoint.of(LocalDateTime.of(2026, 2, 22, 14, 0)));
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, GEORGE), model.getDisplayedContactList());
    }

    @Test
    public void parse_findLastContactedContainsWord_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_LAST_CONTACTED + "year");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 1);
        Predicate<Contact> predicate = contact -> contact.containsInLastContacted("year");
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE), model.getDisplayedContactList());
    }

    @Test
    public void parse_findImpreciseTagName_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_TAG + "friend");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 0);
        Predicate<Contact> predicate = (Contact contact) -> contact.hasTag("friend");
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(), model.getDisplayedContactList());
    }

    @Test
    public void parse_combinedParameters_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_ADDRESS + "street " + PREFIX_TAG + "friends");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 1);
        Predicate<Contact> predicate = new ContactPredicateBuilder().addressContainsKeywords(List.of("street"))
                .tagsHasKeywords(List.of("friends")).build();
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL), model.getDisplayedContactList());
    }

    @Test
    public void parse_associateIndex_returnsFindCommand() throws ParseException {
        FindCommand command = parser.parse(" @1");
        assertEquals(new FindAssociationsCommand(TypicalIndexes.INDEX_FIRST_CONTACT), command);
    }

    @Test
    public void parse_associateIndexInvalid_throwsParseException() {
        assertParseFailure(parser, " @0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}
