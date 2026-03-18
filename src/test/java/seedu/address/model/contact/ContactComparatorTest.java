package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import seedu.address.model.contact.ContactComparator.Field;
import seedu.address.model.contact.ContactComparator.Order;
import seedu.address.testutil.ContactBuilder;

public class ContactComparatorTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactComparator(null, null));
    }

    @Test
    public void hasAllComparators() {
        for (Field field : Field.values()) {
            for (Order order : Order.values()) {
                assertDoesNotThrow(() -> new ContactComparator(field, order));
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
                .withLastUpdated("22/02/26")
                .build();

        Contact bob = new ContactBuilder()
                .withName("Bob")
                .withPhone("22222222")
                .withEmail("bob@test.com")
                .withAddress("B Street")
                .withLastUpdated("23/02/26")
                .build();

        ContactComparator comparator = new ContactComparator(field, Order.ASCENDING);

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
                .withLastUpdated("22/02/26")
                .build();

        Contact bob = new ContactBuilder()
                .withName("Bob")
                .withPhone("22222222")
                .withEmail("bob@test.com")
                .withAddress("B Street")
                .withLastUpdated("23/02/26")
                .build();

        ContactComparator comparator = new ContactComparator(field, Order.DESCENDING);

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

        ContactComparator comparator = new ContactComparator(field, Order.ASCENDING);

        assertTrue(comparator.compare(presentValue, missingValue) < 0);
    }

    private static Stream<Field> nullableFields() {
        return Stream.of(Field.PHONE, Field.EMAIL, Field.ADDRESS);
    }

    @Test
    public void compare_lastUpdatedStringFallback_ascendingUsesLexicalOrder() {
        Contact alpha = new ContactBuilder()
                .withName("Alpha")
                .withLastUpdated("alpha")
                .build();
        Contact zeta = new ContactBuilder()
                .withName("Zeta")
                .withLastUpdated("zeta")
                .build();

        ContactComparator comparator = new ContactComparator(Field.LAST_UPDATED, Order.ASCENDING);

        assertTrue(comparator.compare(alpha, zeta) < 0);
    }
}
