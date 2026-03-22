package seedu.address.model.contact;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.timepoint.TimePointComparator;

/**
 * Comparator for sorting contacts based on specified field and order.
 */
public class ContactFieldComparator extends ContactComparator {
    /** The fields that can be used for sorting contacts. */
    public static enum Field {
        NAME, PHONE, EMAIL, ADDRESS, LAST_CONTACTED, LAST_UPDATED
    }

    @SuppressWarnings("unchecked")
    private static final Map<Field, Map<Order, Comparator<Contact>>> COMPARATORS = Map.of(
            Field.NAME, Map.of(
                    Order.ASCENDING, Comparator.comparing(Contact::getName, Comparator.naturalOrder()),
                    Order.DESCENDING, Comparator.comparing(Contact::getName, Comparator.reverseOrder())),
            Field.PHONE, Map.of(
                    Order.ASCENDING,
                    Comparator.comparing(contact -> contact.getPhone().orElse(null),
                            Comparator.nullsLast(Comparator.naturalOrder())),
                    Order.DESCENDING,
                    Comparator.comparing(contact -> contact.getPhone().orElse(null),
                            Comparator.nullsLast(Comparator.reverseOrder()))),
            Field.EMAIL, Map.of(
                    Order.ASCENDING,
                    Comparator.comparing(contact -> contact.getEmail().orElse(null),
                            Comparator.nullsLast(Comparator.naturalOrder())),
                    Order.DESCENDING,
                    Comparator.comparing(contact -> contact.getEmail().orElse(null),
                            Comparator.nullsLast(Comparator.reverseOrder()))),
            Field.ADDRESS, Map.of(
                    Order.ASCENDING,
                    Comparator.comparing(contact -> contact.getAddress().orElse(null),
                            Comparator.nullsLast(Comparator.naturalOrder())),
                    Order.DESCENDING, Comparator.comparing(contact -> contact.getAddress().orElse(null),
                            Comparator.nullsLast(Comparator.reverseOrder()))),
            Field.LAST_CONTACTED, Map.of(
                    Order.ASCENDING,
                    Comparator.comparing((Contact contact) -> contact.getLastContacted()
                                    .map(lastContacted -> lastContacted.value).orElse(null),
                            Comparator.nullsLast(
                                    TimePointComparator.stringTimePointLast(
                                            TimePointComparator.ifSameDayDateTimePointFirst(
                                                    Comparator.naturalOrder())))),
                    Order.DESCENDING,
                    Comparator.comparing((Contact contact) -> contact.getLastContacted()
                                    .map(lastContacted -> lastContacted.value).orElse(null),
                            Comparator.nullsLast(
                                    TimePointComparator.stringTimePointLast(
                                            TimePointComparator.ifSameDayDateTimePointFirst(
                                                    Comparator.reverseOrder()))))),
            Field.LAST_UPDATED, Map.of(
                    Order.ASCENDING, Comparator.comparing(Contact::getLastUpdated, Comparator.naturalOrder()),
                    Order.DESCENDING, Comparator.comparing(Contact::getLastUpdated, Comparator.reverseOrder())));

    private final Comparator<Contact> comparator;

    /**
     * Constructs a ContactComparator with the specified field and order.
     *
     * @param field The field to sort by.
     * @param order The order to sort in.
     * @throws NullPointerException if either field or order is null.
     */
    public ContactFieldComparator(Field field, Order order) {
        requireAllNonNull(field, order);
        this.comparator = getComparator(field, order);
    }

    private static Comparator<Contact> getComparator(Field field, Order order) {
        Map<Order, Comparator<Contact>> orderMap = COMPARATORS.get(field);
        Comparator<Contact> comparator = orderMap.get(order);
        return comparator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(comparator);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof ContactFieldComparator other)) {
            return false;
        }

        return this.comparator.equals(other.comparator);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("comparator", comparator.toString()).toString();
    }

    @Override
    public int compare(Contact o1, Contact o2) {
        return comparator.compare(o1, o2);
    }
}
