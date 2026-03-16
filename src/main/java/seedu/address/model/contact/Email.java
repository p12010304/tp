package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Contact's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email implements Comparable<Email> {
    // Allowed special characters in local-part
    private static final String SPECIAL_CHARACTERS = "._%+-";

    // Local part: one or more alphanumeric or allowed special characters
    private static final String LOCAL_PART_REGEX = "[A-Za-z0-9](([A-Za-z0-9_+%-]|(\\.(?!\\.)))*[A-Za-z0-9])?";

    private static final String DOMAIN_LABEL = "(?!-)[A-Za-z0-9-]+(?<!-)";

    // Domain part: alphanumeric characters or hyphens, separated by periods, ending with TLD >= 2 chars
    private static final String DOMAIN_REGEX = "(" + DOMAIN_LABEL + "\\.)+" + "[A-Za-z]{2,}";

    // Complete email regex
    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;

    // Constraints message
    public static final String MESSAGE_CONSTRAINTS =
            "Emails should be of the format local-part@domain and adhere to the following constraints:\n"
            + "1. The local-part should only contain alphanumeric characters and the special characters "
            + SPECIAL_CHARACTERS + ", and must:\n"
            + "       - start and end with an alphanumeric character\n"
            + "       - be at least 1 character long\n"
            + "       - not have consecutive periods \n"
            + "2. The domain consists of one or more labels separated by periods.\n"
            + "   Each domain label must:\n"
            + "       - start and end with an alphanumeric character\n"
            + "       - contain only alphanumeric characters or hyphens in between\n"
            + "       - be at least 1 character long\n"
            + "   The last domain label (top-level domain) must be at least 2 characters long, and must be alphabetic.";

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.toLowerCase().equals(otherEmail.value.toLowerCase());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Email o) {
        return this.value.compareToIgnoreCase(o.value);
    }

}
