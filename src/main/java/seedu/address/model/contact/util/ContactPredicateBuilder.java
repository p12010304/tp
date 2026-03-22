package seedu.address.model.contact.util;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AFTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEFORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.TimePointParser;
import seedu.address.model.contact.Contact;

/**
 * A utility class to help with building Contact predicates.
 */
public class ContactPredicateBuilder {
    private Predicate<Contact> contactPredicates;

    public ContactPredicateBuilder() {
        contactPredicates = contact -> true;
    }

    /**
     * Adds predicates that search for keywords in the {@code Contact}.
     */
    public ContactPredicateBuilder containsKeywords(List<String> keywords) {
        for (String keyword : keywords) {
            contactPredicates = contactPredicates.and(contact -> contact.contains(keyword));
        }
        return this;
    }

    /**
     * Adds predicates that search for keywords in the {@code Name} of the {@code Contact}.
     */
    public ContactPredicateBuilder nameContainsKeywords(List<String> keywords) {
        for (String keyword : keywords) {
            contactPredicates = contactPredicates.and(contact -> contact.containsInName(keyword));
        }
        return this;
    }

    /**
     * Adds predicates that search for keywords in the {@code Phone} of the {@code Contact}.
     */
    public ContactPredicateBuilder phoneContainsKeywords(List<String> keywords) {
        for (String keyword : keywords) {
            contactPredicates = contactPredicates.and(contact -> contact.containsInPhone(keyword));
        }
        return this;
    }

    /**
     * Adds predicates that search for keywords in the {@code Email} of the {@code Contact}.
     */
    public ContactPredicateBuilder emailContainsKeywords(List<String> keywords) {
        for (String keyword : keywords) {
            contactPredicates = contactPredicates.and(contact -> contact.containsInEmail(keyword));
        }
        return this;
    }

    /**
     * Adds predicates that search for keywords in the {@code Address} of the {@code Contact}.
     */
    public ContactPredicateBuilder addressContainsKeywords(List<String> keywords) {
        for (String keyword : keywords) {
            contactPredicates = contactPredicates.and(contact -> contact.containsInAddress(keyword));
        }
        return this;
    }

    /**
     * Adds predicates that search for keywords in the {@code Tags} of the {@code Contact}.
     */
    public ContactPredicateBuilder tagsHasKeywords(List<String> keywords) {
        for (String keyword : keywords) {
            contactPredicates = contactPredicates.and(contact -> contact.hasTag(keyword));
        }
        return this;
    }

    /**
     * Adds predicates that search for keywords in the {@code LastContacted} of the {@code Contact}.
     */
    public ContactPredicateBuilder addLastContactedPredicate(List<String> lastContactedValues) {
        for (String lastContactedValue : lastContactedValues) {
            if (lastContactedValue.isBlank()) {
                contactPredicates = contactPredicates.and(contact -> contact.getLastContacted().isPresent());
            }
            lastContactedValue = " " + lastContactedValue; // Prepend a whitespace to make the prefix parsing work
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(lastContactedValue, PREFIX_ON, PREFIX_BEFORE, PREFIX_AFTER);
            for (String keyword : splitKeywords(argMultimap.getPreamble())) {
                contactPredicates = contactPredicates.and(contact -> contact.containsInLastContacted(keyword));
            }
            for (String onValue : argMultimap.getAllValues(PREFIX_ON)) {
                contactPredicates = contactPredicates.and(contact -> contact.lastContactedIsSameDayAs(
                        TimePointParser.toTimePoint(onValue)));
            }
            for (String afterValue : argMultimap.getAllValues(PREFIX_AFTER)) {
                contactPredicates = contactPredicates.and(contact -> contact.lastContactedIsAfter(
                        TimePointParser.toTimePoint(afterValue)));
            }
            for (String beforeValue : argMultimap.getAllValues(PREFIX_BEFORE)) {
                contactPredicates = contactPredicates.and(contact -> contact.lastContactedIsBefore(
                        TimePointParser.toTimePoint(beforeValue)));
            }
        }
        return this;
    }

    public Predicate<Contact> build() {
        return contactPredicates;
    }

    /**
     * Splits a raw keyword string by whitespace and drops empty tokens.
     */
    private List<String> splitKeywords(String rawKeywords) {
        return Arrays.stream(rawKeywords.trim().split("\\s+"))
                .filter(keyword -> !keyword.isBlank())
                .collect(Collectors.toList());
    }
}
