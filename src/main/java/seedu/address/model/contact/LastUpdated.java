package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a contact's last updated time.
 * Guarantees: immutable; value is non-blank.
 */
public class LastUpdated implements Comparable<LastUpdated> {
    public static final String MESSAGE_CONSTRAINTS = "Last updated time should not be blank.";

    public final LocalDateTime value;

    /**
     * Constructs a {@link LastUpdated} from a {@link LocalDateTime}.
     *
     * @param ldt A non-null {@link LocalDateTime}.
     */
    public LastUpdated(LocalDateTime ldt) {
        requireNonNull(ldt);
        value = ldt;
    }

    /**
     * Returns a new {@code LastUpdated} using the current local date-time.
     */
    public static LastUpdated now() {
        return new LastUpdated(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("value", value).toString();
    }

    @Override
    public int compareTo(LastUpdated other) {
        requireNonNull(other);
        return value.toString().compareTo(other.value.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LastUpdated otherLastUpdated)) {
            return false;
        }

        return value.equals(otherLastUpdated.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.toString());
    }
}
