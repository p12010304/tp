package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactComparator;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {
    private static final Comparator<Contact> NAME_COMPARATOR =
        new ContactComparator(ContactComparator.Field.NAME, ContactComparator.Order.ASCENDING);
    private static final Comparator<Contact> EMAIL_COMPARATOR =
        new ContactComparator(ContactComparator.Field.EMAIL, ContactComparator.Order.ASCENDING);
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortByName_success() {
        SortCommand sortByNameCommand = new SortCommand(NAME_COMPARATOR);
        sortByNameCommand.execute(model);
        assertEquals(ALICE, model.getDisplayedContactList().get(0));
    }

    @Test
    public void equals() {
        SortCommand sortByNameCommand = new SortCommand(NAME_COMPARATOR);
        SortCommand sortByEmailCommand = new SortCommand(EMAIL_COMPARATOR);

        // same object -> returns true
        assertEquals(sortByNameCommand, sortByNameCommand);

        // same values -> returns true
        SortCommand sortByNameCommandCopy = new SortCommand(NAME_COMPARATOR);
        assertEquals(sortByNameCommand, sortByNameCommandCopy);

        // different types -> returns false
        assertNotEquals(1, sortByNameCommand);
        assertNotEquals(null, sortByNameCommand);

        // different sort type -> returns false
        assertNotEquals(sortByNameCommand, sortByEmailCommand);
    }

    @Test
    public void sortCommand_toString_notNull() {
        SortCommand sortByNameCommand = new SortCommand(NAME_COMPARATOR);
        assertNotNull(sortByNameCommand.toString());
    }
}
