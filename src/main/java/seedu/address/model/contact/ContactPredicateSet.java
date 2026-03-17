package seedu.address.model.contact;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Collects a set of {@code Predicate<Contact>} and acts as a single {@code Predicate<Contact>}.
 */
public abstract class ContactPredicateSet implements Predicate<Contact> {
    private final Set<Predicate<Contact>> contactPredicates;

    public ContactPredicateSet() {
        this.contactPredicates = new HashSet<>();
    }

    public void addPredicate(Predicate<Contact> predicate) {
        contactPredicates.add(predicate);
    }

    public Set<Predicate<Contact>> getPredicates() {
        return contactPredicates;
    }
}
