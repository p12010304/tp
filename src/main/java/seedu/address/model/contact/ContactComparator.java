package seedu.address.model.contact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * An abstract comparator for sorting contacts based on order.
 */
public abstract class ContactComparator implements Comparator<Contact> {
    /** The orders that can be used for sorting contacts. */
    public static enum Order {
        ASCENDING, DESCENDING
    }

    private static final ContactComparator IDENTITY = new ContactComparator() {
        {
            comparators.clear();
        }

        @Override
        public int compare(Contact o1, Contact o2) {
            return 0;
        }
    };

    protected final List<ContactComparator> comparators = new ArrayList<>(Arrays.asList(this));

    /**
     * Returns a lexicographic-order comparator with another comparator. If this
     * ContactComparator considers two elements equal, i.e. compare(a, b) == 0,
     * other is used to determine the order.
     *
     * The returned comparator is serializable if the specified comparator is also
     * serializable.
     * Specified by: thenComparing(...) in ContactComparator
     *
     * @param other the other comparator to be used when this comparator compares
     *              two
     * @return
     */
    public ContactComparator thenComparing(ContactComparator other) {
        List<ContactComparator> newComparators = new ArrayList<>(this.comparators);
        newComparators.addAll(other.comparators);
        ContactComparator outerThis = this;

        return new ContactComparator() {
            {
                comparators.clear();
                comparators.addAll(newComparators);
            }

            @Override
            public int compare(Contact o1, Contact o2) {
                int result = outerThis.compare(o1, o2);
                return result != 0 ? result : other.compare(o1, o2);
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }

                if (obj == null || !(obj instanceof ContactComparator otherObj)) {
                    return false;
                }

                return this.comparators.equals(otherObj.comparators);
            }
        };
    }

    /**
     * Returns an identity comparator that considers all contacts equal.
     * @return the identity comparator
     */
    public static ContactComparator identity() {
        return IDENTITY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(comparators);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("comparators", comparators)
            .toString();
    }
}
