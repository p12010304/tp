package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Finds and lists contacts whose fields match the given keywords.
 * Keyword matching is case-insensitive.
 */
public class FindFieldsCommand extends FindCommand {

    private final Predicate<Contact> predicate;

    /**
     * Creates a FindFieldsCommand that filters contacts by the given predicate.
     */
    public FindFieldsCommand(Predicate<Contact> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.filterDisplayedContactList(predicate);

        String feedback =
                String.format(Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW, model.getDisplayedContactList().size());
        model.saveSnapshot(feedback);
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FindFieldsCommand)) {
            return false;
        }
        FindFieldsCommand otherCommand = (FindFieldsCommand) other;
        return predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
