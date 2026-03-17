package seedu.address.model.timepoint;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Custom time object that can represent a point in time using a {@code LocalDateTime}.
 */
public class DateTimeTimePoint extends TimePoint<LocalDateTime> {
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
                + ", " + TimePoint.PASCAL_MTHS[localDateTime.getMonthValue() - 1]
                + " " + localDateTime.getDayOfMonth()
                + ", " + localDateTime.getYear();
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
