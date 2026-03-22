package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_CONTACTED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_UPDATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.contact.ContactComparator;
import seedu.address.model.contact.ContactFieldComparator;

/**
 * Sorts and lists contacts by the given fields.
 * Sorting is done by string comparison
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String ASCENDING_KEYWORD = "asc";
    public static final String DESCENDING_KEYWORD = "desc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts contacts by the given fields "
        + "and displays them as a list with index numbers.\n"
        + "Parameters: "
        + "[" + PREFIX_NAME + ASCENDING_KEYWORD + " | " + DESCENDING_KEYWORD + "] "
        + "[" + PREFIX_PHONE + ASCENDING_KEYWORD + " | " + DESCENDING_KEYWORD + "] "
        + "[" + PREFIX_EMAIL + ASCENDING_KEYWORD + " | " + DESCENDING_KEYWORD + "] "
        + "[" + PREFIX_ADDRESS + ASCENDING_KEYWORD + " | " + DESCENDING_KEYWORD + "] "
        + "[" + PREFIX_LAST_CONTACTED + "TAG:" + ASCENDING_KEYWORD + " | " + DESCENDING_KEYWORD + "]...\n"
        + "[" + PREFIX_LAST_UPDATED + "TAG:" + ASCENDING_KEYWORD + " | " + DESCENDING_KEYWORD + "]...\n"
        + "[" + PREFIX_TAG + "TAG:" + ASCENDING_KEYWORD + " | " + DESCENDING_KEYWORD + "]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + ASCENDING_KEYWORD + " "
        + PREFIX_TAG + "friends:" + ASCENDING_KEYWORD;

    // TODO: Use CREATED_ON field when it is implemented
    private static final ContactComparator DEFAULT_COMPARATOR = new ContactFieldComparator(
            ContactFieldComparator.Field.LAST_UPDATED, ContactFieldComparator.Order.ASCENDING);

    private final Optional<ContactComparator> comparator;

    /**
     * Creates a SortCommand with the default sort order.
     */
    public SortCommand() {
        this.comparator = Optional.empty();
    }

    /**
     * Creates a SortCommand with the sort order specified by the given comparator.
     * @param comparator
     */
    public SortCommand(ContactComparator comparator) {
        this.comparator = Optional.of(comparator);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.sortDisplayedContactList(comparator.orElse(DEFAULT_COMPARATOR));

        String feedback = String.format(Messages.MESSAGE_CONTACTS_SORTED_OVERVIEW,
                model.getDisplayedContactList().size());
        model.saveSnapshot(feedback);
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand otherSortCommand)) {
            return false;
        }

        return comparator.equals(otherSortCommand.comparator);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("comparator", comparator)
            .toString();
    }
}
