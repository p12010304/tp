package seedu.address.model.timepoint;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Utility class for {@link LocalDateTime} formatting.
 */
public class DateTimeUtil {
    /**
     * Formats a {@link LocalDateTime} into a displayable string.
     *
     * @param ldt The {@link LocalDateTime} to format.
     * @return The formatted string.
     */
    public static String toDisplayString(LocalDateTime ldt) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, MMM d, yyyy", Locale.ROOT);
        return ldt.format(formatter);
    }
}
