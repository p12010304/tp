package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_CONTACTED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_UPDATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Sorts and lists contacts by the given fields.
 * Sorting is done by string comparison
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String DESCENDING_KEYWORD = "DESC";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts contacts by the given fields "
        + "and displays them as a list with index numbers.\n"
        + "Parameters: "
        + "[" + PREFIX_NAME + "] "
        + "[" + PREFIX_PHONE + "] "
        + "[" + PREFIX_EMAIL + "] "
        + "[" + PREFIX_ADDRESS + "] "
        + "[" + PREFIX_LAST_UPDATED + "] "
        + "[" + PREFIX_LAST_CONTACTED + "] "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_EMAIL + "john@example.com "
        + PREFIX_TAG + "friend";

    private final Comparator<Contact> comparator;

    public SortCommand(Comparator<Contact> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortDisplayedContactList(comparator);

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
