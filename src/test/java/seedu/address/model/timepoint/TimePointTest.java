package seedu.address.model.timepoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TimePointTest {
    @Test
    public void ofTests() {
        assertEquals(TimePoint.of("string"), new StringTimePoint("string"));
        assertEquals(TimePoint.of(LocalDate.of(2026, 10, 20)), new DateTimePoint(LocalDate.of(2026, 10, 20)));
        assertEquals(TimePoint.of(
                LocalDateTime.of(2026, 5, 6, 7, 8)), new DateTimeTimePoint(LocalDateTime.of(2026, 5, 6, 7, 8)));
    }

    @Test
    public void isSameDayAsTests() {
        TimePoint stringDate = TimePoint.of("2024 Oct 10");
        TimePoint stringNotDate = TimePoint.of("lorem ipsum");
        TimePoint date1 = TimePoint.of(LocalDate.of(2024, 5, 10));
        TimePoint date2 = TimePoint.of(LocalDate.of(2024, 5, 11));
        TimePoint dateTime = TimePoint.of(LocalDateTime.of(2024, 5, 10, 10, 0));
        TimePoint sameDayDateTime = TimePoint.of(LocalDateTime.of(2024, 5, 10, 5, 0));
        TimePoint diffDayDateTime = TimePoint.of(LocalDateTime.of(2024, 5, 11, 10, 0));

        //String formatted TimePoint always returns false
        assertFalse(stringDate.isSameDayAs(stringDate));
        assertFalse(stringDate.isSameDayAs(TimePoint.of("2024 Oct 10")));
        assertFalse(stringDate.isSameDayAs(stringNotDate));
        assertFalse(stringDate.isSameDayAs(date1));
        assertFalse(stringDate.isSameDayAs(dateTime));
        assertFalse(stringDate.isSameDayAs(null));

        //Date formatted TimePoint requires same day
        assertTrue(date1.isSameDayAs(date1));
        assertTrue(date1.isSameDayAs(TimePoint.of(LocalDate.of(2024, 5, 10))));
        assertTrue(date1.isSameDayAs(dateTime));
        assertTrue(date1.isSameDayAs(sameDayDateTime));
        assertTrue(date2.isSameDayAs(diffDayDateTime));

        //Date formatted TimePoint returns false when given different day or null
        assertFalse(date1.isSameDayAs(date2));
        assertFalse(date2.isSameDayAs(date1));
        assertFalse(date1.isSameDayAs(stringDate));
        assertFalse(date1.isSameDayAs(stringNotDate));
        assertFalse(date1.isSameDayAs(diffDayDateTime));
        assertFalse(date1.isSameDayAs(null));

        //DateTime formatted TimePoint requires same day
        assertTrue(dateTime.isSameDayAs(dateTime));
        assertTrue(dateTime.isSameDayAs(TimePoint.of(LocalDateTime.of(2024, 5, 10, 10, 0))));
        assertTrue(dateTime.isSameDayAs(sameDayDateTime));
        assertTrue(diffDayDateTime.isSameDayAs(date2));
        assertTrue(dateTime.isSameDayAs(date1));

        //DateTime formatted TimePoint returns false when given different day or null
        assertFalse(dateTime.isSameDayAs(diffDayDateTime));
        assertFalse(diffDayDateTime.isSameDayAs(dateTime));
        assertFalse(diffDayDateTime.isSameDayAs(sameDayDateTime));
        assertFalse(dateTime.isSameDayAs(date2));
        assertFalse(dateTime.isSameDayAs(stringDate));
        assertFalse(dateTime.isSameDayAs(stringNotDate));
        assertFalse(dateTime.isSameDayAs(null));
    }

    @Test
    public void isAfterTests() {
        TimePoint stringDate = TimePoint.of("2024 Oct 10");
        TimePoint stringNotDate = TimePoint.of("lorem ipsum");
        TimePoint date1 = TimePoint.of(LocalDate.of(2024, 5, 10));
        TimePoint date2 = TimePoint.of(LocalDate.of(2024, 5, 11));
        TimePoint dateTime1 = TimePoint.of(LocalDateTime.of(2024, 5, 10, 10, 0));
        TimePoint dateTimeDiffTime = TimePoint.of(LocalDateTime.of(2024, 5, 10, 15, 0));
        TimePoint dateTime2 = TimePoint.of(LocalDateTime.of(2024, 5, 11, 10, 0));

        //String formatted TimePoint always returns false
        assertFalse(stringDate.isAfter(stringDate));
        assertFalse(stringDate.isAfter(TimePoint.of("2024 Oct 10")));
        assertFalse(stringDate.isAfter(stringNotDate));
        assertFalse(stringDate.isAfter(date1));
        assertFalse(stringDate.isAfter(dateTime1));
        assertFalse(stringDate.isAfter(null));

        //Date formatted TimePoint checks only for the day
        assertTrue(date2.isAfter(date1));
        assertTrue(date2.isAfter(TimePoint.of(LocalDate.of(2024, 5, 10))));
        assertTrue(date2.isAfter(dateTime1));

        //Date formatted TimePoint returns false when given same day, later dates or null
        assertFalse(date1.isAfter(date2));
        assertFalse(date1.isAfter(date1));
        assertFalse(date1.isAfter(stringDate));
        assertFalse(date2.isAfter(stringDate));
        assertFalse(date1.isAfter(dateTime1));
        assertFalse(date1.isAfter(dateTime2));
        assertFalse(date2.isAfter(null));

        //DateTime formatted TimePoint checks for both date and time if possible, if given only a date,
        // check only for the date
        assertTrue(dateTime2.isAfter(dateTime1));
        assertTrue(dateTimeDiffTime.isAfter(dateTime1));
        assertTrue(dateTime2.isAfter(dateTimeDiffTime));
        assertTrue(dateTime2.isAfter(date1));

        //DateTime formatted TimePoint returns false when given same time, later times or null
        assertFalse(dateTime1.isAfter(date1));
        assertFalse(dateTime1.isAfter(dateTime1));
        assertFalse(dateTime1.isAfter(date2));
        assertFalse(dateTime1.isAfter(dateTime2));
        assertFalse(dateTime1.isAfter(stringDate));
        assertFalse(dateTime2.isAfter(null));
    }


    @Test
    public void isBeforeTests() {
        TimePoint stringDate = TimePoint.of("2024 Oct 10");
        TimePoint stringNotDate = TimePoint.of("lorem ipsum");
        TimePoint date1 = TimePoint.of(LocalDate.of(2024, 5, 10));
        TimePoint date2 = TimePoint.of(LocalDate.of(2024, 5, 11));
        TimePoint dateTime = TimePoint.of(LocalDateTime.of(2024, 5, 10, 10, 0));
        TimePoint dateTimeDiffTime = TimePoint.of(LocalDateTime.of(2024, 5, 10, 15, 0));
        TimePoint dateTimeDiffDay = TimePoint.of(LocalDateTime.of(2024, 5, 11, 10, 0));

        //String formatted TimePoint always returns false
        assertFalse(stringDate.isBefore(stringDate));
        assertFalse(stringDate.isBefore(TimePoint.of("2024 Oct 10")));
        assertFalse(stringDate.isBefore(stringNotDate));
        assertFalse(stringDate.isBefore(date1));
        assertFalse(stringDate.isBefore(dateTime));
        assertFalse(stringDate.isBefore(null));

        //Date formatted TimePoint checks only for the day
        assertTrue(date1.isBefore(date2));
        assertTrue(date1.isBefore(TimePoint.of(LocalDate.of(2024, 5, 12))));
        assertTrue(date1.isBefore(dateTimeDiffDay));

        //Date formatted TimePoint returns false when given same day, later dates or null
        assertFalse(date1.isBefore(date1));
        assertFalse(date1.isBefore(stringDate));
        assertFalse(date2.isBefore(stringDate));
        assertFalse(date1.isBefore(dateTime));
        assertFalse(date1.isBefore(dateTimeDiffTime));
        assertFalse(date2.isBefore(null));

        //DateTime formatted TimePoint checks for both date and time if possible, if given only a date,
        // check only for the date
        assertTrue(dateTime.isBefore(dateTimeDiffTime));
        assertTrue(dateTimeDiffTime.isBefore(dateTimeDiffDay));
        assertTrue(dateTime.isBefore(date2));
        assertTrue(dateTimeDiffTime.isBefore(date2));

        //DateTime formatted TimePoint returns false when given same time, later times or null
        assertFalse(dateTime.isBefore(date1));
        assertFalse(dateTime.isBefore(dateTime));
        assertFalse(dateTimeDiffTime.isBefore(dateTime));
        assertFalse(dateTimeDiffDay.isBefore(dateTime));
        assertFalse(dateTime.isBefore(stringDate));
        assertFalse(dateTime.isBefore(null));
    }

    @Test
    public void equalsTests() {
        TimePoint stringDate = TimePoint.of("2024 Oct 10");
        TimePoint stringNotDate = TimePoint.of("lorem ipsum");
        TimePoint date1 = TimePoint.of(LocalDate.of(2024, 5, 10));
        TimePoint date2 = TimePoint.of(LocalDate.of(2024, 5, 11));
        TimePoint dateTime = TimePoint.of(LocalDateTime.of(2024, 5, 10, 10, 0));
        TimePoint dateTimeDiffTime = TimePoint.of(LocalDateTime.of(2024, 5, 10, 15, 0));
        TimePoint dateTimeDiffDay = TimePoint.of(LocalDateTime.of(2024, 5, 11, 10, 0));

        //String formatted TimePoint is equal only to another string formatted TimePoint containing the same string
        assertTrue(stringDate.equals(stringDate));
        assertTrue(stringDate.equals(TimePoint.of("2024 Oct 10")));

        assertFalse(stringDate.equals(stringNotDate));
        assertFalse(stringDate.equals("stringNotDate"));
        assertFalse(stringDate.equals(1.5));
        assertFalse(stringDate.equals(null));
        assertFalse(stringDate.equals(date1));
        assertFalse(stringDate.equals(dateTime));

        //Date formatted TimePoint is equal only to another Date formatted TimePoint containing the same date
        assertTrue(date1.equals(date1));
        assertTrue(date1.equals(TimePoint.of(LocalDate.of(2024, 5, 10))));

        assertFalse(date1.equals(date2));
        assertFalse(date1.equals(dateTime));
        assertFalse(date1.equals(stringDate));
        assertFalse(date1.equals(stringNotDate));
        assertFalse(date1.equals("stringNotDate"));
        assertFalse(date1.equals(6.3));
        assertFalse(date1.equals(null));

        //DateTime formatted TimePoint is equal only to another DateTime formatted TimePoint containing the same time
        assertTrue(dateTime.equals(dateTime));
        assertTrue(dateTime.equals(TimePoint.of(LocalDateTime.of(2024, 5, 10, 10, 0))));

        assertFalse(dateTime.equals(date1));
        assertFalse(dateTime.equals(dateTimeDiffTime));
        assertFalse(dateTime.equals(dateTimeDiffDay));
        assertFalse(dateTime.equals(stringDate));
        assertFalse(dateTime.equals(stringNotDate));
        assertFalse(dateTime.equals("stringNotDate"));
        assertFalse(dateTime.equals(6.3));
        assertFalse(dateTime.equals(null));
    }

    @Test
    public void toStringTests() {
        TimePoint t1 = TimePoint.of("Someday");
        assertTrue(t1.toString().equals("Someday"));
        TimePoint t2 = TimePoint.of(LocalDate.of(2024, 1, 1));
        assertTrue(t2.toString().equals("Jan 1, 2024"));
        TimePoint t3 = TimePoint.of(LocalDateTime.of(2024, 1, 19, 10, 0));
        assertTrue(t3.toString().equals("10:00, Jan 19, 2024"));
        TimePoint t4 = TimePoint.of(LocalDateTime.of(2024, 1, 19, 10, 25));
        assertTrue(t4.toString().equals("10:25, Jan 19, 2024"));
    }
}
