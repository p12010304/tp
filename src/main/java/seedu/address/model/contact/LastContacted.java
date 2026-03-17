package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.address.logic.parser.TimePointParser;
import seedu.address.model.timepoint.TimePoint;

/**
 * Represents a contact's last contacted time.
 * Guarantees: immutable; value is non-blank.
 */
public class LastContacted {
    public static final String MESSAGE_CONSTRAINTS = "Last contacted time should not be blank.";

    public final TimePoint<?> value;

    /**
     * Constructs a {@code LastContacted} from raw user input.
     *
     * @param rawLastContacted A non-blank last contacted value.
     */
    public LastContacted(String rawLastContacted) {
        requireNonNull(rawLastContacted);
        String trimmedValue = rawLastContacted.trim();
        checkArgument(isValidLastContacted(trimmedValue), MESSAGE_CONSTRAINTS);
        value = TimePointParser.toTimePoint(trimmedValue);
    }

    /**
     * Constructs a {@code LastContacted} from a {@code TimePoint}.
     *
     * @param timePoint A non-null time point.
     */
    public LastContacted(TimePoint<?> timePoint) {
        requireNonNull(timePoint);
        value = timePoint;
    }

    /**
     * Returns true if a given string is a valid last contacted input.
     */
    public static boolean isValidLastContacted(String test) {
        return test != null && !test.trim().isEmpty();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LastContacted)) {
            return false;
        }
        LastContacted otherLastContacted = (LastContacted) other;
        return value.equals(otherLastContacted.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.toString());
    }
}
