package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class FileCardTest extends GuiUnitTest {
    @Test
    public void constructor_success() throws Exception {
        runAndWait(() -> {
            FileCardDetails details = new FileCardDetails("file.json", LocalDateTime.of(2026, 3, 20, 10, 39), 10);
            assertDoesNotThrow(() -> new FileCard(details));
        });
    }
}
