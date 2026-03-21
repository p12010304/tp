package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Note;



public class NoteAddCommandTest {
    private static final Note NOTE = new Note("Lorem ipsum");
    private static final List<Note> NOTES = List.of(NOTE);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_success() {
        Contact contactToEdit = model.getDisplayedContactList().get(0);
        Contact editedContact = new Contact(contactToEdit.getName(), contactToEdit.getPhone(), contactToEdit.getEmail(),
                contactToEdit.getAddress(), contactToEdit.getLastContacted(), NOTES, contactToEdit.getTags());
        NoteAddCommand notesCommand = new NoteAddCommand(INDEX_FIRST_CONTACT, NOTE);
        String expectedMessage = String.format(NoteAddCommand.MESSAGE_ADD_NOTES_SUCCESS,
                Messages.format(editedContact));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getDisplayedContactList().get(0), editedContact);

        assertCommandSuccess(notesCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayedContactList().size() + 1);
        NoteAddCommand notesCommand = new NoteAddCommand(outOfBoundIndex, NOTE);

        assertCommandFailure(notesCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final NoteAddCommand standardCommand = new NoteAddCommand(INDEX_FIRST_CONTACT, NOTE);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new NoteAddCommand(INDEX_SECOND_CONTACT, NOTE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new NoteAddCommand(INDEX_FIRST_CONTACT, new Note("Likes ice cream"))));
    }

    @Test
    public void execute_noteWithAtIndex_resolvesToUuid() {
        // Note with @2 should resolve to the UUID of the second contact
        Note noteWithRef = new Note("worked with @2");
        NoteAddCommand command = new NoteAddCommand(INDEX_FIRST_CONTACT, noteWithRef);

        Contact firstContact = model.getDisplayedContactList().get(0);
        Contact secondContact = model.getDisplayedContactList().get(1);
        String expectedRef = "@{" + secondContact.getId().toString() + "}";

        Note resolvedNote = new Note("worked with " + expectedRef);
        Contact editedContact = new Contact(firstContact.getName(), firstContact.getPhone(),
                firstContact.getEmail(), firstContact.getAddress(), firstContact.getLastContacted(),
                List.of(resolvedNote), firstContact.getTags());

        String expectedMessage = String.format(NoteAddCommand.MESSAGE_ADD_NOTES_SUCCESS,
                Messages.format(editedContact));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(firstContact, editedContact);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noteWithOutOfRangeIndex_leftAsLiteral() throws CommandException {
        // @999 is out of range, should be left as literal text
        Note noteWithBadRef = new Note("talked to @999");
        NoteAddCommand command = new NoteAddCommand(INDEX_FIRST_CONTACT, noteWithBadRef);
        CommandResult result = command.execute(model);

        // The note should still contain @999 as-is
        Contact updated = model.getDisplayedContactList().get(0);
        Note addedNote = updated.getNotes().get(updated.getNotes().size() - 1);
        assertTrue(addedNote.value.contains("@999"));
    }

    @Test
    public void execute_noteWithNoRefs_noChange() throws CommandException {
        // Note without any @INDEX references should be added as-is
        Note plainNote = new Note("no references here");
        NoteAddCommand command = new NoteAddCommand(INDEX_FIRST_CONTACT, plainNote);
        command.execute(model);

        Contact updated = model.getDisplayedContactList().get(0);
        Note addedNote = updated.getNotes().get(updated.getNotes().size() - 1);
        assertEquals("no references here", addedNote.value);
    }
}
