package seedu.address.model.timepoint;

/**
 * Custom time object that can represent a point in time using a {@code String}.
 */
public class StringTimePoint extends TimePoint<String> {
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
