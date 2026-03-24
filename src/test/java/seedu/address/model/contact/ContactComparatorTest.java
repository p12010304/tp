package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import seedu.address.model.contact.ContactComparator.Order;
import seedu.address.model.contact.ContactFieldComparator.Field;
import seedu.address.testutil.ContactBuilder;

public class ContactComparatorTest {
    private final ContactComparator identityComparator = ContactComparator.identity();
    private final ContactComparator comparatorA1 = new ContactFieldComparator(Field.NAME, Order.ASCENDING);
    private final ContactComparator comparatorA2 = ContactComparator.identity()
            .thenComparing(new ContactFieldComparator(Field.NAME, Order.ASCENDING));
    private final ContactComparator comparatorB1 = new ContactFieldComparator(Field.LAST_CONTACTED, Order.DESCENDING)
            .thenComparing(new ContactTagComparator("friends", Order.DESCENDING))
            .thenComparing(new ContactFieldComparator(Field.PHONE, Order.ASCENDING));
    private final ContactComparator comparatorB2 = new ContactFieldComparator(Field.LAST_CONTACTED, Order.DESCENDING)
            .thenComparing(new ContactTagComparator("friends", Order.DESCENDING)
            .thenComparing(new ContactFieldComparator(Field.PHONE, Order.ASCENDING)));
    private final ContactComparator comparatorB3 = new ContactFieldComparator(Field.LAST_CONTACTED, Order.DESCENDING)
            .thenComparing(ContactComparator.identity())
            .thenComparing(new ContactTagComparator("friends", Order.DESCENDING))
            .thenComparing(new ContactFieldComparator(Field.PHONE, Order.ASCENDING));

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactFieldComparator(null, null));
    }

    @Test
    public void equals_withThenComparing() {
        assertEquals(identityComparator, ContactComparator.identity());

        assertEquals(comparatorA1, comparatorA2);

        assertEquals(comparatorB1, comparatorB2);
        assertEquals(comparatorB1, comparatorB3);
        assertEquals(comparatorB2, comparatorB3);

        assertNotEquals(comparatorA2, comparatorB1);
    }

    @Test
    public void toString_withThenComparing() {
        assertEquals("ContactComparator{comparators=[]}", identityComparator.toString());

        assertEquals("seedu.address.model.contact.ContactFieldComparator{field=NAME, order=ASCENDING}",
                comparatorA1.toString());
        assertEquals(
                "ContactComparator{comparators=["
                        + "seedu.address.model.contact.ContactFieldComparator{field=NAME, order=ASCENDING}]}",
                comparatorA2.toString());

        String expectedBString = "ContactComparator{comparators=["
                + "seedu.address.model.contact.ContactFieldComparator{field=LAST_CONTACTED, order=DESCENDING}, "
                + "seedu.address.model.contact.ContactTagComparator{tag=friends, order=DESCENDING}, "
                + "seedu.address.model.contact.ContactFieldComparator{field=PHONE, order=ASCENDING}]}";
        assertEquals(expectedBString,
                comparatorB1.toString());
        assertEquals(expectedBString,
                comparatorB2.toString());
        assertEquals(expectedBString,
                comparatorB3.toString());
    }

    @Test
    public void hasAllComparators() {
        for (Field field : Field.values()) {
            for (Order order : Order.values()) {
                assertDoesNotThrow(() -> new ContactFieldComparator(field, order));
            }
        }
    }

    @ParameterizedTest
    @MethodSource("ascendingComparators")
    public void compare_ascending_correctOrder(Field field) {
        Contact alice = new ContactBuilder()
                .withName("Alice")
                .withPhone("11111111")
                .withEmail("alice@test.com")
                .withAddress("A Street")
                .withLastContacted("22/02/26")
                .withLastUpdated(LocalDateTime.of(2026, 2, 22, 9, 0))
                .build();

        Contact bob = new ContactBuilder()
                .withName("Bob")
                .withPhone("22222222")
                .withEmail("bob@test.com")
                .withAddress("B Street")
                .withLastContacted("23/02/26")
                .withLastUpdated(LocalDateTime.of(2026, 2, 23, 9, 0))
                .build();

        ContactFieldComparator comparator = new ContactFieldComparator(field, Order.ASCENDING);

        assertTrue(comparator.compare(alice, bob) < 0);
    }

    private static Stream<Field> ascendingComparators() {
        return Stream.of(Field.values());
    }

    @ParameterizedTest
    @MethodSource("descendingComparators")
    public void compare_descending_correctOrder(Field field) {
        Contact alice = new ContactBuilder()
                .withName("Alice")
                .withPhone("11111111")
                .withEmail("alice@test.com")
                .withAddress("A Street")
                .withLastContacted("22/02/26")
                .withLastUpdated(LocalDateTime.of(2026, 2, 22, 9, 0))
                .build();

        Contact bob = new ContactBuilder()
                .withName("Bob")
                .withPhone("22222222")
                .withEmail("bob@test.com")
                .withAddress("B Street")
                .withLastContacted("23/02/26")
                .withLastUpdated(LocalDateTime.of(2026, 2, 23, 9, 0))
                .build();

        ContactFieldComparator comparator = new ContactFieldComparator(field, Order.DESCENDING);

        assertTrue(comparator.compare(alice, bob) > 0);
    }

    private static Stream<Field> descendingComparators() {
        return Stream.of(Field.values());
    }

    @ParameterizedTest
    @MethodSource("nullableFields")
    public void compare_nullValues_sortedLast(Field field) {
        Contact missingValue;
        Contact presentValue;

        switch (field) {
        case PHONE:
            missingValue = new ContactBuilder().withPhone(null).build();
            presentValue = new ContactBuilder().withPhone("99999999").build();
            break;
        case EMAIL:
            missingValue = new ContactBuilder().withEmail(null).build();
            presentValue = new ContactBuilder().withEmail("test@test.com").build();
            break;
        case ADDRESS:
            missingValue = new ContactBuilder().withAddress(null).build();
            presentValue = new ContactBuilder().withAddress("Somewhere").build();
            break;
        default:
            return;
        }

        ContactFieldComparator comparator = new ContactFieldComparator(field, Order.ASCENDING);

        assertTrue(comparator.compare(presentValue, missingValue) < 0);
    }

    private static Stream<Field> nullableFields() {
        return Stream.of(Field.PHONE, Field.EMAIL, Field.ADDRESS);
    }

    @Test
    public void compare_lastUpdated_ascending() {
        Contact alpha = new ContactBuilder()
                .withName("Alpha")
                .withLastUpdated(LocalDateTime.now().minusDays(1))
                .build();
        Contact zeta = new ContactBuilder()
                .withName("Zeta")
                .withLastUpdated(LocalDateTime.now().minusDays(2))
                .build();

        ContactFieldComparator comparator = new ContactFieldComparator(Field.LAST_UPDATED, Order.ASCENDING);

        assertTrue(comparator.compare(alpha, zeta) > 0);
    }
}
