package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Reminder;
import seedu.address.model.contact.TimePoint;

public class ReminderRemoveCommandTest {
    private static final String REMINDER_NOTE = "Lorem ipsum";
    private static final TimePoint TIME_POINT = TimePoint.of(LocalDate.of(2024, 4, 1));
    private static final Reminder REMINDER = new Reminder(REMINDER_NOTE, TIME_POINT);
    private static final List<Reminder> REMINDERS = List.of(REMINDER);
    private static final int REMOVE_ONE_LINE = 1;
    private static final int REMOVE_TWO_LINES = 2;

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_success() {
        ReminderRemoveCommand reminderCommand = new ReminderRemoveCommand(INDEX_FIRST_CONTACT, REMOVE_ONE_LINE);

        Contact contactToEdit = model.getFilteredContactList().get(0);
        Contact editedContact = new Contact(contactToEdit.getName(), contactToEdit.getPhone(), contactToEdit.getEmail(),
                contactToEdit.getAddress(), contactToEdit.getNotes(), contactToEdit.getTags(), REMINDERS);

        String expectedMessage = String.format(ReminderRemoveCommand.MESSAGE_REMOVE_REMINDERS_SUCCESS,
                Messages.format(contactToEdit));
        Model testModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        testModel.setContact(model.getFilteredContactList().get(0), editedContact);

        assertCommandSuccess(reminderCommand, testModel, expectedMessage, model);
    }

    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        ReminderRemoveCommand reminderCommand = new ReminderRemoveCommand(outOfBoundIndex, REMOVE_ONE_LINE);

        assertCommandFailure(reminderCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ReminderRemoveCommand standardCommand = new ReminderRemoveCommand(INDEX_FIRST_CONTACT, REMOVE_ONE_LINE);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ReminderRemoveCommand(INDEX_SECOND_CONTACT, REMOVE_ONE_LINE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new ReminderRemoveCommand(INDEX_FIRST_CONTACT, REMOVE_TWO_LINES)));
    }
}
