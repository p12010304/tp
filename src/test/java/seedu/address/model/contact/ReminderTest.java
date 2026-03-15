package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ReminderTest {
    @Test
    public void equals() {
        Reminder firstReminder = new Reminder("Christmas meeting", TimePoint.of(LocalDate.of(2026, 12, 20)));

        // same values -> returns true
        assertTrue(firstReminder.equals(new Reminder("Christmas meeting", TimePoint.of(LocalDate.of(2026, 12, 20)))));

        // same object -> returns true
        assertTrue(firstReminder.equals(firstReminder));

        // null -> returns false
        assertFalse(firstReminder.equals(null));

        // different types -> returns false
        assertFalse(firstReminder.equals(5.0f));

        // different note -> returns false
        assertFalse(firstReminder.equals(new Reminder("General meeting", TimePoint.of(LocalDate.of(2026, 12, 20)))));

        // different timePoint -> returns false
        assertFalse(firstReminder.equals(new Reminder("Christmas meeting", TimePoint.of(LocalDate.of(2026, 12, 24)))));

        // different values -> returns false
        assertFalse(firstReminder.equals(new Reminder("General meeting", TimePoint.of(LocalDate.of(2026, 11, 19)))));
    }

    @Test
    public void toStringTest() {
        Reminder notelessReminderWithString = new Reminder("", TimePoint.of("Time"));
        assertEquals("Reminder: Time", notelessReminderWithString.toString());

        Reminder notelessReminderWithDate = new Reminder("", TimePoint.of(LocalDate.of(2026, 12, 7)));
        assertEquals("Reminder: Dec 7 2026", notelessReminderWithDate.toString());

        Reminder notelessReminderWithDateTime = new Reminder("", TimePoint.of(LocalDateTime.of(2026, 10, 19, 13, 5)));
        assertEquals("Reminder: 13:05 Oct 19 2026", notelessReminderWithDateTime.toString());

        Reminder notedReminderWithString = new Reminder("Note", TimePoint.of("Time"));
        assertEquals("Reminder: Note on Time", notedReminderWithString.toString());

        Reminder notedReminderWithDate = new Reminder("Note", TimePoint.of(LocalDate.of(2026, 12, 7)));
        assertEquals("Reminder: Note on Dec 7 2026", notedReminderWithDate.toString());

        Reminder notedReminderWithDateTime = new Reminder("Note", TimePoint.of(LocalDateTime.of(2026, 10, 19, 13, 5)));
        assertEquals("Reminder: Note on 13:05 Oct 19 2026", notedReminderWithDateTime.toString());
    }
}
