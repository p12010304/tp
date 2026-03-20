package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tag names should be alphanumeric";
    public static final String TAGSET_MESSAGE_CONSTRAINTS = "Tag names should be unique";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String name;

    /**
     * Constructs a {@code Tag}.
     *
     * @param name A valid tag name.
     */
    public Tag(String name) {
        requireNonNull(name);
        checkArgument(isValidTagName(name), MESSAGE_CONSTRAINTS);
        this.name = name;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag otherTag)) {
            return false;
        }

        return name.equals(otherTag.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Formats state as text for viewing.
     */
    @Override
    public String toString() {
        return '[' + name + ']';
    }

}
