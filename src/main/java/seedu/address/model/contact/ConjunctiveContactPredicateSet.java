package seedu.address.model.contact;

/**
 * Tests that a {@code Contact}'s passes every predicate in a set of {@code Predicate<Contact>}.
 */
public class ConjunctiveContactPredicateSet extends ContactPredicateSet {
    /**
     * Creates an empty {@code ConjunctiveContactPredicateSet}.
     */
    public ConjunctiveContactPredicateSet() {
        super();
    }

    @Override
    public boolean test(Contact contact) {
        return getPredicates().stream().allMatch(predicate -> predicate.test(contact));
    }
}
