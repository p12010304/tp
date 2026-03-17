package seedu.address.testutil;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.model.contact.ConjunctiveContactPredicateSet;
import seedu.address.model.contact.Contact;

/**
 * A utility class to help with building Contact predicates.
 */
public class ContactPredicateBuilder {
    private final ConjunctiveContactPredicateSet contactPredicates;

    public ContactPredicateBuilder() {
        contactPredicates = new ConjunctiveContactPredicateSet();
    }

    /**
     * Adds predicates that search for keywords in the {@code Contact}.
     */
    public ContactPredicateBuilder containsKeywords(String ... keywords) {
        Arrays.asList(keywords).forEach(
                keyword -> contactPredicates.addPredicate((Contact contact) -> contact.contains(keyword)));
        return this;
    }

    /**
     * Adds predicates that search for keywords in the {@code Name} of the {@code Contact}.
     */
    public ContactPredicateBuilder nameContainsKeywords(String ... keywords) {
        Arrays.asList(keywords).forEach(
                keyword -> contactPredicates.addPredicate((Contact contact) -> contact.containsInName(keyword)));
        return this;
    }

    /**
     * Adds predicates that search for keywords in the {@code Phone} of the {@code Contact}.
     */
    public ContactPredicateBuilder phoneContainsKeywords(String ... keywords) {
        Arrays.asList(keywords).forEach(
                keyword -> contactPredicates.addPredicate((Contact contact) -> contact.containsInPhone(keyword)));
        return this;
    }

    /**
     * Adds predicates that search for keywords in the {@code Email} of the {@code Contact}.
     */
    public ContactPredicateBuilder emailContainsKeywords(String ... keywords) {
        Arrays.asList(keywords).forEach(
                keyword -> contactPredicates.addPredicate((Contact contact) -> contact.containsInEmail(keyword)));
        return this;
    }

    /**
     * Adds predicates that search for keywords in the {@code Address} of the {@code Contact}.
     */
    public ContactPredicateBuilder addressContainsKeywords(String ... keywords) {
        Arrays.asList(keywords).forEach(
                keyword -> contactPredicates.addPredicate((Contact contact) -> contact.containsInAddress(keyword)));
        return this;
    }

    /**
     * Adds predicates that search for keywords in the {@code Tags} of the {@code Contact}.
     */
    public ContactPredicateBuilder tagsHasKeywords(String ... keywords) {
        Arrays.asList(keywords).forEach(
                keyword -> contactPredicates.addPredicate((Contact contact) -> contact.hasTag(keyword)));
        return this;
    }

    public Predicate<Contact> build() {
        return contactPredicates;
    }
}
