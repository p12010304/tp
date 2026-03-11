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

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ViewCommand}.
 */
public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Contact contactToView = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_CONTACT);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_CONTACT_SUCCESS,
                contactToView.getName().fullName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, contactToView);
        assertCommandSuccess(viewCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        Contact contactToView = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_CONTACT);

        String expectedMessage = String.format(ViewCommand.MESSAGE_VIEW_CONTACT_SUCCESS,
                contactToView.getName().fullName);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showContactAtIndex(expectedModel, INDEX_FIRST_CONTACT);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, contactToView);
        assertCommandSuccess(viewCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        Index outOfBoundIndex = INDEX_SECOND_CONTACT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());

        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewCommand viewFirstCommand = new ViewCommand(INDEX_FIRST_CONTACT);
        ViewCommand viewSecondCommand = new ViewCommand(INDEX_SECOND_CONTACT);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(INDEX_FIRST_CONTACT);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewCommand viewCommand = new ViewCommand(targetIndex);
        String expected = ViewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, viewCommand.toString());
    }
}
