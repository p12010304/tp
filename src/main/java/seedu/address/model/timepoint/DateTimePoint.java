package seedu.address.model.timepoint;

import java.time.LocalDate;

/**
 * Custom time object that can represent a point in time using a {@code LocalDate}.
 */
public class DateTimePoint extends TimePoint<LocalDate> {
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
        return TimePoint.PASCAL_MTHS[localDate.getMonthValue() - 1]
                + " " + localDate.getDayOfMonth()
                + ", " + localDate.getYear();
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
