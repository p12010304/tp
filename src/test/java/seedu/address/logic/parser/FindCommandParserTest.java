package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
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

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.ContactPredicateBuilder;

public class FindCommandParserTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_findKeyword_test() throws ParseException {
        FindCommand command = parser.parse("ne");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 4);
        Predicate<Contact> predicate = (Contact contact) -> contact.contains("ne");
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL, ELLE), model.getFilteredContactList());
    }

    @Test
    public void parse_findName_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_NAME + "Elle");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 1);
        Predicate<Contact> predicate = (Contact contact) -> contact.containsInName("Elle");
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE), model.getFilteredContactList());
    }

    @Test
    public void parse_findPhone_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_PHONE + "94");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 4);
        Predicate<Contact> predicate = (Contact contact) -> contact.containsInPhone("94");
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, ELLE, FIONA, GEORGE), model.getFilteredContactList());
    }

    @Test
    public void parse_findEmail_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_EMAIL + "ANNA");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 1);
        Predicate<Contact> predicate = (Contact contact) -> contact.containsInEmail("ANNA");
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GEORGE), model.getFilteredContactList());
    }

    @Test
    public void parse_findAddress_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_ADDRESS + "street");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 3);
        Predicate<Contact> predicate = (Contact contact) -> contact.containsInAddress("street");
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL, GEORGE), model.getFilteredContactList());
    }

    @Test
    public void parse_findTags_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_TAG + "friends");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 3);
        Predicate<Contact> predicate = (Contact contact) -> contact.hasTag("friends");
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredContactList());
    }

    @Test
    public void parse_findImpreciseTagName_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_TAG + "friend");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 0);
        Predicate<Contact> predicate = (Contact contact) -> contact.hasTag("friend");
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(), model.getFilteredContactList());
    }

    @Test
    public void parse_combinedParameters_test() throws ParseException {
        FindCommand command = parser.parse(" " + PREFIX_ADDRESS + "street " + PREFIX_TAG + "friends");
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 1);
        Predicate<Contact> predicate =
                new ContactPredicateBuilder().addressContainsKeywords("street").tagsHasKeywords("friends").build();
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL), model.getFilteredContactList());
    }
}
