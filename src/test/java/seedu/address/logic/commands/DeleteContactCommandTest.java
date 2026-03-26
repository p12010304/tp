package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showContactAtIndex;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Note;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteContactCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Contact contactToDelete = model.getDisplayedContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteContactCommand(INDEX_FIRST_CONTACT);

        String expectedMessage = String.format(DeleteContactCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayedContactList().size() + 1);
        DeleteCommand deleteCommand = new DeleteContactCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model,
                Messages.getIndexOutOfRangeMessage(model.getDisplayedContactList().size()));
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        Contact contactToDelete = model.getDisplayedContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteContactCommand(INDEX_FIRST_CONTACT);

        String expectedMessage = String.format(DeleteContactCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);
        showNoContact(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        Index outOfBoundIndex = INDEX_SECOND_CONTACT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());

        DeleteCommand deleteCommand = new DeleteContactCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model,
                Messages.getIndexOutOfRangeMessage(model.getDisplayedContactList().size()));
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteContactCommand(INDEX_FIRST_CONTACT);
        DeleteCommand deleteSecondCommand = new DeleteContactCommand(INDEX_SECOND_CONTACT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteContactCommand(INDEX_FIRST_CONTACT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteContactCommand(targetIndex);
        String expected = DeleteContactCommand.class.getCanonicalName() + "{index=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void execute_deleteContactReferencedInNotes_dereferencesNotes() throws Exception {
        // Add a note to contact 2 that references contact 1
        Contact contact1 = model.getDisplayedContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        Contact contact2 = model.getDisplayedContactList().get(INDEX_SECOND_CONTACT.getZeroBased());
        String refStr = "@{" + contact1.getId().toString() + "}";
        Note noteWithRef = new Note("worked with " + refStr);
        Contact contact2WithNote = new Contact(contact2.getId(), contact2.getName(),
                contact2.getPhone(), contact2.getEmail(), contact2.getAddress(),
                contact2.getLastContacted(), contact2.getLastUpdated(),
                List.of(noteWithRef), contact2.getTags());
        model.setContact(contact2, contact2WithNote);

        // Delete contact 1
        DeleteCommand deleteCommand = new DeleteContactCommand(INDEX_FIRST_CONTACT);
        deleteCommand.execute(model);

        // Contact 2's note should now have the plain name instead of @{UUID}
        Contact updatedContact2 = model.getAddressBook().getContactList().stream()
                .filter(c -> c.getId().equals(contact2.getId()))
                .findFirst()
                .orElseThrow();
        String expectedNoteText = "worked with " + contact1.getName().fullName;
        assertEquals(expectedNoteText, updatedContact2.getNotes().get(0).value);
    }

    /**
     * Updates {@code model}'s displayed list to show no one.
     */
    private void showNoContact(Model model) {
        model.filterDisplayedContactList(p -> false);

        assertTrue(model.getDisplayedContactList().isEmpty());
    }
}
