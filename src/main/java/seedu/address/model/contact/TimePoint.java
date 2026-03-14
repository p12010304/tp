package seedu.address.model.contact;

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

    /** Strings for abbreviations of months of the year for toString() output. */
    private static final String[] Mths = {
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private final T value;

    public TimePoint(T value) {
        this.value = value;
    }

    /**
     * Creates a new TimePoint with the time in string format.
     *
     * @param time String that represents a time.
     */
    public static TimePoint of(String time) {
        return new StringTimePoint(time);
    }

    /**
     * Creates a new TimePoint with the time in LocalDate format.
     *
     * @param localDate LocalDate that represents a time.
     */
    public static TimePoint of(LocalDate localDate) {
        return new DateTimePoint(localDate);
    }

    /**
     * Creates a new TimePoint with the time in LocalDateTime format.
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

    private static class StringTimePoint extends TimePoint<String> {
        public StringTimePoint(String timeString) {
            super(timeString);
        }

        @Override
        public boolean isSameDayAs(TimePoint other) {
            return false;
        }

        @Override
        public boolean isAfter(TimePoint other) {
            return false;
        }

        @Override
        public boolean isBefore(TimePoint other) {
            return false;
        }

        @Override
        public String toString() {
            return this.getTime();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof StringTimePoint otherStringTimePoint)) {
                return false;
            }

            return this.getTime().equals(otherStringTimePoint.getTime());
        }
    }

    private static class DateTimePoint extends TimePoint<LocalDate> {
        public DateTimePoint(LocalDate localDate) {
            super(localDate);
        }

        @Override
        public boolean isSameDayAs(TimePoint other) {
            if (other instanceof StringTimePoint) {
                return false;
            }
            if (other instanceof DateTimePoint) {
                return this.getTime().equals(((DateTimePoint) other).getTime());
            }
            if (other instanceof DateTimeTimePoint) {
                return this.getTime().equals(((DateTimeTimePoint) other).getTime().toLocalDate());
            }
            return false;
        }

        @Override
        public boolean isAfter(TimePoint other) {
            if (other instanceof StringTimePoint) {
                return false;
            }
            if (other instanceof DateTimePoint) {
                return this.getTime().isAfter(((DateTimePoint) other).getTime());
            }
            if (other instanceof DateTimeTimePoint) {
                return this.getTime().isAfter(((DateTimeTimePoint) other).getTime().toLocalDate());
            }
            return false;
        }

        @Override
        public boolean isBefore(TimePoint other) {
            if (other instanceof StringTimePoint) {
                return false;
            }
            if (other instanceof DateTimePoint) {
                return this.getTime().isBefore(((DateTimePoint) other).getTime());
            }
            if (other instanceof DateTimeTimePoint) {
                return this.getTime().isBefore(((DateTimeTimePoint) other).getTime().toLocalDate());
            }
            return false;
        }

        @Override
        public String toString() {
            LocalDate localDate = this.getTime();
            return Mths[localDate.getMonthValue() - 1]
                    + " " + localDate.getDayOfMonth()
                    + " " + localDate.getYear();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DateTimePoint otherDateTimePoint)) {
                return false;
            }

            return this.getTime().equals(otherDateTimePoint.getTime());
        }
    }

    private static class DateTimeTimePoint extends TimePoint<LocalDateTime> {
        public DateTimeTimePoint(LocalDateTime localDateTime) {
            super(localDateTime);
        }

        @Override
        public boolean isSameDayAs(TimePoint other) {
            if (other instanceof StringTimePoint) {
                return false;
            }
            LocalDate thisLocalDate = this.getTime().toLocalDate();
            if (other instanceof DateTimePoint) {
                return thisLocalDate.equals(((DateTimePoint) other).getTime());
            }
            if (other instanceof DateTimeTimePoint) {
                return thisLocalDate.equals(((DateTimeTimePoint) other).getTime().toLocalDate());
            }
            return false;
        }

        @Override
        public boolean isAfter(TimePoint other) {
            if (other instanceof StringTimePoint) {
                return false;
            }
            if (other instanceof DateTimePoint) {
                return this.getTime().toLocalDate().isAfter(((DateTimePoint) other).getTime());
            }
            if (other instanceof DateTimeTimePoint) {
                return this.getTime().isAfter(((DateTimeTimePoint) other).getTime());
            }
            return false;
        }

        @Override
        public boolean isBefore(TimePoint other) {
            if (other instanceof StringTimePoint) {
                return false;
            }
            if (other instanceof DateTimePoint) {
                return this.getTime().toLocalDate().isBefore(((DateTimePoint) other).getTime());
            }
            if (other instanceof DateTimeTimePoint) {
                return this.getTime().isBefore(((DateTimeTimePoint) other).getTime());
            }
            return false;
        }

        @Override
        public String toString() {
            LocalDateTime localDateTime = this.getTime();
            return localDateTime.getHour()
                    + ":" + (localDateTime.getMinute() < 10 ? "0" : "") + localDateTime.getMinute()
                    + " " + Mths[localDateTime.getMonthValue() - 1]
                    + " " + localDateTime.getDayOfMonth()
                    + " " + localDateTime.getYear();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DateTimeTimePoint otherDateTimeTimePoint)) {
                return false;
            }

            return this.getTime().equals(otherDateTimeTimePoint.getTime());
        }
    }
}
