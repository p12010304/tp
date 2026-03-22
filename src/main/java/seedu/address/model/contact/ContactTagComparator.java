package seedu.address.model.contact;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.RankedTag;

/**
 * Comparator for tags within contacts.
 */
public final class ContactTagComparator extends ContactComparator {
    private final String tag;
    private final Order order;

    /**
     * Constructs a ContactTagComparator for the specified tag and order.
     *
     * @param tag   The tag to sort by.
     * @param order The order to sort in.
     * @throws NullPointerException if either field or order is null.
     */
    public ContactTagComparator(String tag, Order order) {
        requireAllNonNull(tag, order);

        this.tag = tag;
        this.order = order;
    }

    /**
     * Extract a specific {@code RankedTag} from a {@code Contact}.
     *
     * @param contact The contact with the tag.
     * @param tagName The name of the tag.
     * @return The {@code RankedTag} if present, nothing otherwise.
     */
    private static Optional<RankedTag> extractRankedTag(Contact contact, String tagName) {
        return contact.getTags().stream()
            .filter(tag -> tag.name.equals(tagName) && tag instanceof RankedTag)
            .map(tag -> (RankedTag) tag)
            .findFirst();
    }

    @Override
    public int compare(Contact o1, Contact o2) {
        // Neither has the Tag
        if (!o1.hasTag(tag) && !o2.hasTag(tag)) {
            return 0;
        }

        // Only one of the two has a Tag
        if (!o1.hasTag(tag) || !o2.hasTag(tag)) {
            return o1.hasTag(tag) ? -1 : 1;
        }

        Optional<RankedTag> rankedTag1 = extractRankedTag(o1, tag);
        Optional<RankedTag> rankedTag2 = extractRankedTag(o2, tag);

        // Both have the Tag (but not the RankedTag)
        if (rankedTag1.isEmpty() && rankedTag2.isEmpty()) {
            return 0;
        }

        // Only one of the two has a RankedTag
        if (rankedTag1.isEmpty() || rankedTag2.isEmpty()) {
            return rankedTag1.isPresent() ? -1 : 1;
        }

        // Both have RankedTags
        return order == Order.ASCENDING
                ? rankedTag1.get().rank.compareToIgnoreCase(rankedTag2.get().rank)
                : rankedTag2.get().rank.compareToIgnoreCase(rankedTag1.get().rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, order);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof ContactTagComparator other)) {
            return false;
        }

        return this.tag.equals(other.tag) && this.order.equals(other.order);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("tag", tag)
            .add("order", order)
            .toString();
    }
}
