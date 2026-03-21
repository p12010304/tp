package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BENSON;
import static seedu.address.testutil.TypicalContacts.DANIEL;
import static seedu.address.testutil.TypicalContacts.ELLE;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.ContactBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Predicate<Contact> firstPredicate = (Contact contact) -> contact.contains("first");
        Predicate<Contact> secondPredicate = (Contact contact) -> contact.contains("second");

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_singleContactFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 1);
        Predicate<Contact> predicate = (Contact contact) -> contact.containsInName("Elle");
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE), model.getDisplayedContactList());
    }

    @Test
    public void execute_multipleContactsFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 4);
        Predicate<Contact> predicate = (Contact contact) -> contact.contains("ne");
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL, ELLE), model.getDisplayedContactList());
    }

    @Test
    public void execute_crossRefWithSharedTags_success() throws CommandException {
        // BENSON (index 2) has tags "owesMoney" and "friends". ALICE and DANIEL also have "friends" tag.
        FindCommand command = new FindCommand(INDEX_SECOND_CONTACT);
        CommandResult result = command.execute(model);
        assertTrue(result.getFeedbackToUser().contains("Cross-referencing"));
        assertTrue(model.getDisplayedContactList().size() > 0);
    }

    @Test
    public void execute_crossRefNoTags_returnsNoTagsMessage() throws CommandException {
        AddressBook ab = new AddressBook();
        Contact noTagContact = new ContactBuilder().withName("No Tag Person")
                .withPhone("91234567").withEmail("notag@example.com").build();
        ab.addContact(noTagContact);
        Model testModel = new ModelManager(ab, new UserPrefs());

        FindCommand command = new FindCommand(INDEX_FIRST_CONTACT);
        CommandResult result = command.execute(testModel);
        assertTrue(result.getFeedbackToUser().contains("has no tags"));
    }

    @Test
    public void execute_crossRefInvalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayedContactList().size() + 1);
        FindCommand command = new FindCommand(outOfBoundIndex);
        try {
            command.execute(model);
            assertTrue(false, "Expected CommandException");
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX, e.getMessage());
        }
    }

    @Test
    public void execute_crossRefNoRelatedContacts_returnsNoRelatedMessage() throws CommandException {
        // Create an address book with one contact that has a unique tag no one else shares
        AddressBook ab = new AddressBook();
        Contact uniqueTagContact = new ContactBuilder().withName("Unique Tag Person")
                .withPhone("91234567").withEmail("unique@example.com")
                .withTags("uniqueOnlyTag").build();
        Contact otherContact = new ContactBuilder().withName("Other Person")
                .withPhone("98765432").withEmail("other@example.com")
                .withTags("differentTag").build();
        ab.addContact(uniqueTagContact);
        ab.addContact(otherContact);
        Model testModel = new ModelManager(ab, new UserPrefs());

        FindCommand command = new FindCommand(INDEX_FIRST_CONTACT);
        CommandResult result = command.execute(testModel);
        assertTrue(result.getFeedbackToUser().contains("No other contacts share tags"));
    }

    @Test
    public void equals_crossRef() {
        FindCommand findFirst = new FindCommand(INDEX_FIRST_CONTACT);
        FindCommand findSecond = new FindCommand(INDEX_SECOND_CONTACT);

        assertTrue(findFirst.equals(findFirst));
        assertTrue(findFirst.equals(new FindCommand(INDEX_FIRST_CONTACT)));
        assertFalse(findFirst.equals(findSecond));
        assertFalse(findFirst.equals(null));
        assertFalse(findFirst.equals(1));
    }
}
