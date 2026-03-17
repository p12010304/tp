package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BOB;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.timepoint.TimePoint;
import seedu.address.testutil.ContactBuilder;

public class ContactTest {
    private static final Contact JOHN = new ContactBuilder()
            .withName("John Smith").withPhone("94455028").withEmail("john@email.com")
            .withAddress("Block 470, J Street 92").withTags("friends", "contractor")
            .withNotes("Met in 2024 February.").build();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Contact contact = new ContactBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> contact.getTags().remove(0));
    }

    @Test
    public void isSameContact() {
        // same object -> returns true
        assertTrue(ALICE.isSameContact(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameContact(null));

        // same name, all other attributes different -> returns true
        Contact editedAlice = new ContactBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(ALICE.isSameContact(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new ContactBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameContact(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Contact editedBob = new ContactBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameContact(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new ContactBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameContact(editedBob));
    }

    @Test
    public void containsTest() {
        assertTrue(JOHN.contains("John"));
        assertTrue(JOHN.contains("9445"));
        assertTrue(JOHN.contains("com"));
        assertTrue(JOHN.contains("block"));
        assertTrue(JOHN.contains("FRIEND"));
        assertTrue(JOHN.contains("February"));
        assertFalse(JOHN.contains("Singapore"));
    }

    @Test
    public void containsInNameTest() {
        assertTrue(JOHN.containsInName(""));
        assertTrue(JOHN.containsInName("John"));
        assertTrue(JOHN.containsInName("JO"));
        assertTrue(JOHN.containsInName("smit"));
        assertFalse(JOHN.containsInName("Kenny"));
    }

    @Test
    public void containsInPhoneTest() {
        assertTrue(JOHN.containsInPhone(""));
        assertTrue(JOHN.containsInPhone("9445"));
        assertTrue(JOHN.containsInPhone("45"));
        assertTrue(JOHN.containsInPhone("028"));
        assertFalse(JOHN.containsInPhone("John"));
    }

    @Test
    public void containsInAddressTest() {
        assertTrue(JOHN.containsInAddress(""));
        assertTrue(JOHN.containsInAddress("Block"));
        assertTrue(JOHN.containsInAddress("street"));
        assertTrue(JOHN.containsInAddress("92"));
        assertFalse(JOHN.containsInAddress("55"));
    }

    @Test
    public void containsInEmailTest() {
        assertTrue(JOHN.containsInEmail(""));
        assertTrue(JOHN.containsInEmail("john"));
        assertTrue(JOHN.containsInEmail("email"));
        assertTrue(JOHN.containsInEmail("com"));
        assertFalse(JOHN.containsInEmail("47"));
    }

    @Test
    public void containsInTagsTest() {
        assertTrue(JOHN.containsInTags(""));
        assertTrue(JOHN.containsInTags("FRIENd"));
        assertTrue(JOHN.containsInTags("friends"));
        assertTrue(JOHN.containsInTags("contractor"));
        assertFalse(JOHN.containsInTags("doctor"));
    }

    @Test
    public void hasTagTest() {
        assertFalse(JOHN.hasTag(""));
        assertFalse(JOHN.hasTag("FRIENd"));
        assertTrue(JOHN.hasTag("FRIENDS"));
        assertTrue(JOHN.hasTag("contractor"));
        assertFalse(JOHN.hasTag("doctor"));
    }

    @Test
    public void containsInNotesTest() {
        assertTrue(JOHN.containsInNotes("2024"));
        assertTrue(JOHN.containsInNotes("feb"));
        assertTrue(JOHN.containsInNotes("In"));
        assertFalse(JOHN.containsInNotes("John"));
    }

    @Test
    public void remindersTest() {
        String notes = "notes";
        TimePoint dueReminderTime = TimePoint.of(LocalDate.now().plusDays(Note.DUE_PERIOD_DAYS - 1));
        String dueReminderString = "notes " + PREFIX_ON + dueReminderTime.toString();
        Note dueReminder = new Note("notes", dueReminderTime);
        TimePoint notDueReminderTime = TimePoint.of(LocalDate.now().minusDays(1));
        Note notDueReminder = new Note("notes", notDueReminderTime);
        String notDueReminderString = "notes " + PREFIX_ON + notDueReminderTime.toString();

        //tests for hasDueReminders()
        assertFalse(new ContactBuilder().build().hasDueReminders());
        assertFalse(new ContactBuilder().withNotes(notes).build().hasDueReminders());
        assertFalse(new ContactBuilder().withNotes(notDueReminderString).build().hasDueReminders());
        assertTrue(new ContactBuilder().withNotes(dueReminderString).build().hasDueReminders());
        assertTrue(new ContactBuilder().withNotes(notDueReminderString, dueReminderString).build().hasDueReminders());

        //tests for getReminders()
        assertEquals(List.of(), new ContactBuilder().build().getReminders());
        assertEquals(List.of(), new ContactBuilder().withNotes(notes).build().getReminders());
        assertEquals(
                List.of(notDueReminder),
                new ContactBuilder().withNotes(notDueReminderString).build().getReminders());
        assertEquals(
                List.of(dueReminder),
                new ContactBuilder().withNotes(dueReminderString).build().getReminders());
        assertEquals(
                List.of(dueReminder, notDueReminder),
                new ContactBuilder().withNotes(notes, dueReminderString, notDueReminderString).build().getReminders());

        //tests for getDueReminders()
        assertEquals(List.of(), new ContactBuilder().build().getDueReminders());
        assertEquals(List.of(), new ContactBuilder().withNotes(notes).build().getDueReminders());
        assertEquals(List.of(), new ContactBuilder().withNotes(notDueReminderString).build().getDueReminders());
        assertEquals(
                List.of(dueReminder),
                new ContactBuilder().withNotes(dueReminderString).build().getDueReminders());
        assertEquals(
                List.of(dueReminder),
                new ContactBuilder().withNotes(
                        notes, dueReminderString, notDueReminderString).build().getDueReminders());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Contact aliceCopy = new ContactBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different contact -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Contact editedAlice = new ContactBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ContactBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ContactBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ContactBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ContactBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Contact.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
