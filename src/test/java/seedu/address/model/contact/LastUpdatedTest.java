package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class LastUpdatedTest {
    @Test
    public void equals() {
        LastUpdated first = new LastUpdated(LocalDateTime.of(2026, 2, 22, 9, 0));
        LastUpdated second = new LastUpdated(LocalDateTime.of(2026, 2, 23, 9, 0));
        LastUpdated firstCopy = new LastUpdated(LocalDateTime.of(2026, 2, 22, 9, 0));

        assertTrue(first.equals(first));
        assertTrue(first.equals(firstCopy));
        assertFalse(first.equals(1));
        assertFalse(first.equals(null));
        assertFalse(first.equals(second));
    }

    @Test
    public void toStringValue() {
        LastUpdated lastUpdated = new LastUpdated(LocalDateTime.of(2026, 2, 22, 9, 0));
        assertEquals("seedu.address.model.contact.LastUpdated{value=2026-02-22T09:00}", lastUpdated.toString());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LastUpdated(null));
    }

    @Test
    public void hashCode_equalObjects_sameHashCode() {
        LastUpdated first = new LastUpdated(LocalDateTime.of(2026, 2, 22, 9, 0));
        LastUpdated second = new LastUpdated(LocalDateTime.of(2026, 2, 22, 9, 0));
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void compareTo_sameValue_returnsZero() {
        LastUpdated first = new LastUpdated(LocalDateTime.of(2026, 2, 22, 9, 0));
        LastUpdated second = new LastUpdated(LocalDateTime.of(2026, 2, 22, 9, 0));
        assertEquals(0, first.compareTo(second));
    }

    @Test
    public void compareTo_chronologicalValues_ordersByTime() {
        LastUpdated earlier = new LastUpdated(LocalDateTime.of(2026, 2, 22, 9, 0));
        LastUpdated later = new LastUpdated(LocalDateTime.of(2026, 2, 23, 9, 0));

        assertTrue(earlier.compareTo(later) < 0);
        assertTrue(later.compareTo(earlier) > 0);
    }

    @Test
    public void compareTo_nullOther_throwsNullPointerException() {
        LastUpdated value = new LastUpdated(LocalDateTime.of(2026, 2, 22, 9, 0));
        assertThrows(NullPointerException.class, () -> value.compareTo(null));
    }

    @Test
    public void now_returnsCurrentDateTime() {
        LastUpdated value = LastUpdated.now();
        assertNotNull(value.value);
    }
}
