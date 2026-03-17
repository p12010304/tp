package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.timepoint.TimePoint;

public class NoteTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Note(null));
    }

    @Test
    public void isReminderTest() {
        assertFalse(new Note("Reminder note").isReminder());
        assertTrue(new Note("Reminder note", TimePoint.of("timeString")).isReminder());
        assertTrue(new Note("Reminder note", TimePoint.of(LocalDate.of(2025, 10, 25))).isReminder());
        assertTrue(new Note("Reminder note", TimePoint.of(LocalDateTime.of(2025, 10, 25, 15, 35))).isReminder());
    }

    @Test
    public void hasDueReminderTest() {
        int dueDayThreshold = Note.DUE_PERIOD_DAYS - 1;
        assertFalse(new Note("Reminder note").hasDueReminder());
        assertFalse(new Note("Reminder note", TimePoint.of("timeString")).hasDueReminder());
        assertTrue(
                new Note(
                        "Reminder note",
                        TimePoint.of(LocalDate.now().plusDays(Note.DUE_PERIOD_DAYS - 1))).hasDueReminder());
        assertFalse(
                new Note(
                        "Reminder note",
                        TimePoint.of(LocalDate.now().plusDays(Note.DUE_PERIOD_DAYS + 1))).hasDueReminder());
        assertTrue(
                new Note(
                        "Reminder note",
                        TimePoint.of(LocalDateTime.now().plusDays(Note.DUE_PERIOD_DAYS - 1))).hasDueReminder());
        assertFalse(
                new Note(
                        "Reminder note",
                        TimePoint.of(LocalDateTime.now().plusDays(Note.DUE_PERIOD_DAYS + 1))).hasDueReminder());
    }

    @Test
    public void jsonTest() {
        assertEquals(new Note("sample"), Note.fromJsonString(new Note("sample").toJsonString()));
        assertEquals(
                new Note("reminder", TimePoint.of("time")),
                Note.fromJsonString(new Note("reminder", TimePoint.of("time")).toJsonString()));
        assertEquals(
                new Note("reminder", TimePoint.of(LocalDate.of(2026, 7, 7))),
                Note.fromJsonString(new Note("reminder", TimePoint.of(LocalDate.of(2026, 7, 7))).toJsonString()));
        assertEquals(
                new Note("reminder", TimePoint.of(LocalDateTime.of(2026, 7, 7, 13, 30))),
                Note.fromJsonString(
                        new Note("reminder", TimePoint.of(LocalDateTime.of(2026, 7, 7, 13, 30))).toJsonString()));
    }

    @Test
    public void equals() {
        Note notes = new Note("To meet on February");

        // same values -> returns true
        assertTrue(notes.equals(new Note("To meet on February")));

        // same object -> returns true
        assertTrue(notes.equals(notes));

        // null -> returns false
        assertFalse(notes.equals(null));

        // different types -> returns false
        assertFalse(notes.equals(5.0f));

        // different values -> returns false
        assertFalse(notes.equals(new Note("Likes ice cream")));
    }
}
