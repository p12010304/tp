package seedu.address.logic.commands;

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
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Note;

public class NoteRemoveCommandTest {
    private static final Note NOTE = new Note("Lorem ipsum");
    private static final List<Note> NOTES = List.of(NOTE);
    private static final int REMOVE_ONE_LINE = 1;
    private static final int REMOVE_TWO_LINES = 2;

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_success() {
        NoteRemoveCommand notesCommand = new NoteRemoveCommand(INDEX_FIRST_CONTACT, REMOVE_ONE_LINE);

        Contact contactToEdit = model.getDisplayedContactList().get(0);
        Contact editedContact = new Contact(contactToEdit.getName(), contactToEdit.getPhone(), contactToEdit.getEmail(),
                contactToEdit.getAddress(), NOTES, contactToEdit.getTags());

        String expectedMessage = String.format(NoteRemoveCommand.MESSAGE_REMOVE_NOTES_SUCCESS,
                Messages.format(contactToEdit));
        Model testModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        testModel.setContact(model.getDisplayedContactList().get(0), editedContact);

        assertCommandSuccess(notesCommand, testModel, expectedMessage, model);
    }

    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayedContactList().size() + 1);
        NoteRemoveCommand notesCommand = new NoteRemoveCommand(outOfBoundIndex, REMOVE_ONE_LINE);

        assertCommandFailure(notesCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final NoteRemoveCommand standardCommand = new NoteRemoveCommand(INDEX_FIRST_CONTACT, REMOVE_ONE_LINE);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new NoteRemoveCommand(INDEX_SECOND_CONTACT, REMOVE_ONE_LINE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new NoteRemoveCommand(INDEX_FIRST_CONTACT, REMOVE_TWO_LINES)));
    }
}
