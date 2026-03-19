package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Contact;

public class SnapshotTest {
    @Test
    public void equalsTest() {
        Model model = new ModelManager();
        Snapshot snapshot = model.getSnapshot();
        assertTrue(snapshot.equals(snapshot));
        assertTrue(snapshot.equals(model.getSnapshot()));
        assertFalse(snapshot.equals(null));
        assertFalse(snapshot.equals(model));
        Snapshot filledPredicateSnapshot =
                new Snapshot(
                        model.getAddressBook().getContactList(),
                        model.getUserPrefs(), (Contact contact) -> contact.contains("sample"), null);
        assertFalse(model.equals(filledPredicateSnapshot));
        assertFalse(filledPredicateSnapshot.equals(model));
        Snapshot emptyContactListSnapshot =
                new Snapshot(List.of(), model.getUserPrefs(), null, null);
        assertFalse(model.equals(emptyContactListSnapshot));
        Snapshot emptyUserPrefsSnapshot =
                new Snapshot(model.getAddressBook().getContactList(), null, null, null);
        assertFalse(model.equals(emptyUserPrefsSnapshot));
    }
}
