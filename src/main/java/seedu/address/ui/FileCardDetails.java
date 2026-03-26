package seedu.address.ui;

import java.time.LocalDateTime;

/**
 * Record to contain the information required by a {@code FileCard}.
 * Implemented to reduce the amount of times a file needs to be parsed
 * @param name Name of the valid B2B4U file.
 * @param lastModifiedTime The {@code lastModified} value of the file.
 * @param contactCount Number of contacts in the B2B4U file.
 */
public record FileCardDetails(String name, LocalDateTime lastModifiedTime, int contactCount) {
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof FileCardDetails otherFileCardDetails)) {
            return false;
        }

        return this.name.equals(otherFileCardDetails.name);
    }
}
