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
import java.util.UUID;
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
 * Contains integration tests (interaction with the Model) for {@code FindCommand} subclasses.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals_findFieldsCommand() {
        Predicate<Contact> firstPredicate = (Contact contact) -> contact.contains("first");
        Predicate<Contact> secondPredicate = (Contact contact) -> contact.contains("second");

        FindFieldsCommand findFirstCommand = new FindFieldsCommand(firstPredicate);
        FindFieldsCommand findSecondCommand = new FindFieldsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindFieldsCommand findFirstCommandCopy = new FindFieldsCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_singleContactFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 1);
        Predicate<Contact> predicate = (Contact contact) -> contact.containsInName("Elle");
        FindFieldsCommand command = new FindFieldsCommand(predicate);
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE), model.getDisplayedContactList());
    }

    @Test
    public void execute_multipleContactsFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 4);
        Predicate<Contact> predicate = (Contact contact) -> contact.contains("ne");
        FindFieldsCommand command = new FindFieldsCommand(predicate);
        expectedModel.filterDisplayedContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, ELLE, ALICE, BENSON), model.getDisplayedContactList());
    }

    @Test
    public void execute_crossRefWithNoteReferences_success() throws CommandException {
        // Create contacts where the first contact's note references the second contact's UUID
        UUID secondId = UUID.randomUUID();
        AddressBook ab = new AddressBook();
        Contact first = new ContactBuilder().withName("Alice")
                .withPhone("91234567").withEmail("alice@example.com")
                .withNotes("meeting with @{" + secondId + "}").build();
        Contact second = new ContactBuilder().withName("Bob").withId(secondId)
                .withPhone("98765432").withEmail("bob@example.com").build();
        ab.addContact(first);
        ab.addContact(second);
        Model testModel = new ModelManager(ab, new UserPrefs());

        FindAssociationsCommand command = new FindAssociationsCommand(INDEX_FIRST_CONTACT);
        CommandResult result = command.execute(testModel);
        assertTrue(result.getFeedbackToUser().contains("Cross-referencing"));
        // Should show both the target (Alice) and the referenced contact (Bob)
        assertEquals(2, testModel.getDisplayedContactList().size());
    }

    @Test
    public void execute_crossRefNoNoteReferences_returnsNoRefsMessage() throws CommandException {
        AddressBook ab = new AddressBook();
        Contact noRefContact = new ContactBuilder().withName("No Ref Person")
                .withPhone("91234567").withEmail("noref@example.com").build();
        ab.addContact(noRefContact);
        Model testModel = new ModelManager(ab, new UserPrefs());

        FindAssociationsCommand command = new FindAssociationsCommand(INDEX_FIRST_CONTACT);
        CommandResult result = command.execute(testModel);
        assertTrue(result.getFeedbackToUser().contains("No contact references found in notes"));
    }

    @Test
    public void execute_crossRefInvalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayedContactList().size() + 1);
        FindAssociationsCommand command = new FindAssociationsCommand(outOfBoundIndex);
        try {
            command.execute(model);
            assertTrue(false, "Expected CommandException");
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX, e.getMessage());
        }
    }

    @Test
    public void execute_crossRefWithNotesButNoNoteRefs_returnsNoRefsMessage() throws CommandException {
        // Contact has notes but no @{UUID} references
        AddressBook ab = new AddressBook();
        Contact contactWithPlainNotes = new ContactBuilder().withName("Plain Notes Person")
                .withPhone("91234567").withEmail("plain@example.com")
                .withNotes("just a regular note").build();
        Contact otherContact = new ContactBuilder().withName("Other Person")
                .withPhone("98765432").withEmail("other@example.com").build();
        ab.addContact(contactWithPlainNotes);
        ab.addContact(otherContact);
        Model testModel = new ModelManager(ab, new UserPrefs());

        FindAssociationsCommand command = new FindAssociationsCommand(INDEX_FIRST_CONTACT);
        CommandResult result = command.execute(testModel);
        assertTrue(result.getFeedbackToUser().contains("No contact references found in notes"));
    }

    @Test
    public void equals_findAssociationsCommand() {
        FindAssociationsCommand findFirst = new FindAssociationsCommand(INDEX_FIRST_CONTACT);
        FindAssociationsCommand findSecond = new FindAssociationsCommand(INDEX_SECOND_CONTACT);

        assertTrue(findFirst.equals(findFirst));
        assertTrue(findFirst.equals(new FindAssociationsCommand(INDEX_FIRST_CONTACT)));
        assertFalse(findFirst.equals(findSecond));
        assertFalse(findFirst.equals(null));
        assertFalse(findFirst.equals(1));
    }

    @Test
    public void findCommand_isAbstract() {
        // Both subclasses are instances of FindCommand
        FindFieldsCommand fieldsCommand = new FindFieldsCommand(c -> true);
        FindAssociationsCommand assocCommand = new FindAssociationsCommand(INDEX_FIRST_CONTACT);
        assertTrue(fieldsCommand instanceof FindCommand);
        assertTrue(assocCommand instanceof FindCommand);
    }
}
