package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @Test
    public void validFileNameTest() {
        assertTrue(UserPrefs.isValidFileName("newbook"));
        assertTrue(UserPrefs.isValidFileName("new_book"));
        assertTrue(UserPrefs.isValidFileName("New_Book123"));
        assertFalse(UserPrefs.isValidFileName("newbook!"));
        assertFalse(UserPrefs.isValidFileName("newbook$"));
    }

    @Test
    public void formatAddressBookFilePathTest() {
        assertEquals(UserPrefs.formatAddressBookFilePath("newbook"), Paths.get("data", "newbook.json"));
        assertEquals(UserPrefs.formatAddressBookFilePath("New_Book123"), Paths.get("data", "New_Book123.json"));
        assertThrows(NullPointerException.class, () -> UserPrefs.formatAddressBookFilePath(null));
        assertThrows(AssertionError.class, () -> UserPrefs.formatAddressBookFilePath("@newbook"));
        assertThrows(AssertionError.class, () -> UserPrefs.formatAddressBookFilePath(""));
    }
}
