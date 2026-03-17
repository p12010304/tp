package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LastContactedTest {

    @Test
    public void isValidLastContacted() {
        assertFalse(LastContacted.isValidLastContacted(null));
        assertFalse(LastContacted.isValidLastContacted(""));
        assertFalse(LastContacted.isValidLastContacted("   "));
        assertTrue(LastContacted.isValidLastContacted("22/02/26"));
    }

    @Test
    public void equals() {
        LastContacted first = new LastContacted("22/02/26");
        LastContacted second = new LastContacted("23/02/26");
        LastContacted firstCopy = new LastContacted("22/02/26");

        assertTrue(first.equals(first));
        assertTrue(first.equals(firstCopy));
        assertFalse(first.equals(1));
        assertFalse(first.equals(null));
        assertFalse(first.equals(second));
    }

    @Test
    public void toStringValue() {
        LastContacted lastContacted = new LastContacted("22/02/26");
        assertEquals("22/02/26", lastContacted.toString());
    }
}
