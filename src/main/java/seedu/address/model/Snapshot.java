package seedu.address.model;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import seedu.address.model.contact.Contact;

/**
 * A record to act as the snapshot of the current {@code Model}.
 *
 * @param contactList List of contacts from the {@code AddressBook} of the {@code Model}.
 * @param userPrefs {@code ReadOnlyUserPrefs} of the {@code Model}.
 * @param filterPredicate {@code Predicate<Contact>} of the {@code FilteredList<Contact>} of the {@code Model}.
 */
public record Snapshot(
        List<Contact> contactList, ReadOnlyUserPrefs userPrefs,
        Predicate<Contact> filterPredicate, Comparator<Contact> sortComparator) {

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Snapshot otherSnapshot)) {
            return false;
        }

        boolean contactsMatch = contactList.size() == otherSnapshot.contactList.size()
                && IntStream.range(0, contactList.size()).allMatch(
                        i -> contactList.get(i).equals(otherSnapshot.contactList.get(i)));

        boolean filterMatch;
        if (filterPredicate == null && otherSnapshot.filterPredicate == null) {
            filterMatch = true;
        } else if (filterPredicate == null || otherSnapshot.filterPredicate == null) {
            filterMatch = false;
        } else {
            filterMatch = filterPredicate.equals(otherSnapshot.filterPredicate);
        }

        boolean comparatorMatch;
        if (sortComparator == null && otherSnapshot.sortComparator == null) {
            comparatorMatch = true;
        } else if (sortComparator == null || otherSnapshot.sortComparator == null) {
            comparatorMatch = false;
        } else {
            comparatorMatch = sortComparator.equals(otherSnapshot.sortComparator);
        }

        return contactsMatch
                && userPrefs.equals(otherSnapshot.userPrefs)
                && filterMatch
                && comparatorMatch;
    }
}
