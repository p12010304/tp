package seedu.address.model.timepoint;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Custom time object that can represent a point in time using either string, LocalDate or LocalDateTime
 */

public abstract class TimePoint<T> {
    /** Strings for months of the year. */
    public static final String[] MONTHS = {
        "JANUARY",
        "FEBRUARY",
        "MARCH",
        "APRIL",
        "MAY",
        "JUNE",
        "JULY",
        "AUGUST",
        "SEPTEMBER",
        "OCTOBER",
        "NOVEMBER",
        "DECEMBER"};

    /** Strings for abbreviations of months of the year in full-uppercase. */
    public static final String[] MTHS = {
        "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    /** Strings for abbreviations of months of the year for toString() output in PascalCase. */
    public static final String[] PASCAL_MTHS = {
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private final T value;

    public TimePoint(T value) {
        this.value = value;
    }

    /**
     * Creates a new TimePoint with the time in {@code String} format.
     *
     * @param time String that represents a time.
     */
    public static TimePoint of(String time) {
        return new StringTimePoint(time);
    }

    /**
     * Creates a new TimePoint with the time in {@code LocalDate} format.
     *
     * @param localDate LocalDate that represents a time.
     */
    public static TimePoint of(LocalDate localDate) {
        return new DateTimePoint(localDate);
    }

    /**
     * Creates a new TimePoint with the time in {@code LocalDateTime} format.
     *
     * @param localDateTime LocalDateTime that represents a time.
     */
    public static TimePoint of(LocalDateTime localDateTime) {
        return new DateTimeTimePoint(localDateTime);
    }

    /**
     * Returns the time this TimePoint contains.
     *
     * @return The time this TimePoint contains that is not null.
     */
    public T getTime() {
        return value;
    }

    /**
     * Compares against another TimePoint if they are on the same day.
     * If either TimePoint being compared contains their date as a string, always return false.
     *
     * @param other Other TimePoint to compare against.
     * @return True only if both TimePoints are on the same day, given they are not stored as a string.
     */
    public abstract boolean isSameDayAs(TimePoint other);

    /**
     * Compares against another TimePoint to check if this TimePoint is after it.
     * If either TimePoint being compared contains their date as a string, always return false.
     *
     * @param other Other TimePoint to compare against
     * @return True only if this TimePoint is after other, given they are not stored as a string.
     */
    public abstract boolean isAfter(TimePoint other);

    /**
     * Compares against another TimePoint to check if this TimePoint is before it.
     * If either TimePoint being compared contains their date as a string, always return false.
     *
     * @param other Other TimePoint to compare against
     * @return True only if this TimePoint is before other, given they are not stored as a string.
     */
    public abstract boolean isBefore(TimePoint other);
}
