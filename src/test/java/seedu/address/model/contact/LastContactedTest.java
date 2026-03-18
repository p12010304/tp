package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.timepoint.TimePoint;

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

    @Test
    public void constructor_nullString_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LastContacted((String) null));
    }

    @Test
    public void constructor_nullTimePoint_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LastContacted((TimePoint<?>) null));
    }

    @Test
    public void constructor_trimmedInput_success() {
        LastContacted lastContacted = new LastContacted("  22/02/26  ");
        assertEquals("22/02/26", lastContacted.toString());
    }

    @Test
    public void hashCode_equalObjects_sameHashCode() {
        LastContacted first = new LastContacted("22/02/26");
        LastContacted second = new LastContacted("22/02/26");
        assertEquals(first.hashCode(), second.hashCode());
    }
}
