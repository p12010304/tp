package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.timepoint.TimePoint;

public class LastUpdatedTest {

    @Test
    public void isValidLastUpdated() {
        assertFalse(LastUpdated.isValidLastUpdated(null));
        assertFalse(LastUpdated.isValidLastUpdated(""));
        assertFalse(LastUpdated.isValidLastUpdated("   "));
        assertTrue(LastUpdated.isValidLastUpdated("22/02/26"));
    }

    @Test
    public void equals() {
        LastUpdated first = new LastUpdated("22/02/26");
        LastUpdated second = new LastUpdated("23/02/26");
        LastUpdated firstCopy = new LastUpdated("22/02/26");

        assertTrue(first.equals(first));
        assertTrue(first.equals(firstCopy));
        assertFalse(first.equals(1));
        assertFalse(first.equals(null));
        assertFalse(first.equals(second));
    }

    @Test
    public void toStringValue() {
        LastUpdated lastUpdated = new LastUpdated("22/02/26");
        assertEquals("22/02/26", lastUpdated.toString());
    }

    @Test
    public void constructor_nullString_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LastUpdated((String) null));
    }

    @Test
    public void constructor_nullTimePoint_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LastUpdated((TimePoint<?>) null));
    }

    @Test
    public void constructor_trimmedInput_success() {
        LastUpdated lastUpdated = new LastUpdated("  22/02/26  ");
        assertEquals("22/02/26", lastUpdated.toString());
    }

    @Test
    public void hashCode_equalObjects_sameHashCode() {
        LastUpdated first = new LastUpdated("22/02/26");
        LastUpdated second = new LastUpdated("22/02/26");
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void compareTo_sameValue_returnsZero() {
        LastUpdated first = new LastUpdated("22/02/26");
        LastUpdated second = new LastUpdated("22/02/26");

        assertEquals(0, first.compareTo(second));
    }

    @Test
    public void compareTo_chronologicalValues_ordersByTime() {
        LastUpdated earlier = new LastUpdated(TimePoint.of(LocalDateTime.of(2026, 2, 22, 9, 0)));
        LastUpdated later = new LastUpdated(TimePoint.of(LocalDateTime.of(2026, 2, 23, 9, 0)));

        assertTrue(earlier.compareTo(later) < 0);
        assertTrue(later.compareTo(earlier) > 0);
    }

    @Test
    public void compareTo_nonChronologicalStringValues_fallsBackToLexicalOrder() {
        LastUpdated alpha = new LastUpdated("alpha");
        LastUpdated zeta = new LastUpdated("zeta");

        assertTrue(alpha.compareTo(zeta) < 0);
        assertTrue(zeta.compareTo(alpha) > 0);
    }

    @Test
    public void compareTo_nullOther_throwsNullPointerException() {
        LastUpdated value = new LastUpdated("22/02/26");
        assertThrows(NullPointerException.class, () -> value.compareTo(null));
    }

    @Test
    public void now_returnsCurrentTimePoint() {
        LastUpdated value = LastUpdated.now();
        assertNotNull(value.value);
    }
}
