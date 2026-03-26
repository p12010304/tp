package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class FileCardDetailsTest {
    @Test
    public void equals() {
        FileCardDetails details = new FileCardDetails("file.json", LocalDateTime.of(2026, 3, 20, 10, 39), 10);
        assertTrue(details.equals(details));

        assertFalse(details.equals(null));

        assertFalse(details.equals(1.5));

        FileCardDetails detailsCopy = new FileCardDetails("file.json", LocalDateTime.of(2026, 3, 20, 10, 39), 10);

        assertTrue(details.equals(detailsCopy));
    }
}
