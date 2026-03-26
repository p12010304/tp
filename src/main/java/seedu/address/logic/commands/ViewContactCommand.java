package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Views a contact's detailed information identified using its displayed index from the contact list.
 */
public class ViewContactCommand extends ViewCommand {

    public static final String MESSAGE_VIEW_CONTACT_SUCCESS = "Viewing contact: %1$s";

    private final Index index;

    public ViewContactCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getDisplayedContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.getIndexOutOfRangeMessage(lastShownList.size()));
        }

        Contact contactToView = lastShownList.get(index.getZeroBased());
        return new ViewContactCommandResult(
                String.format(MESSAGE_VIEW_CONTACT_SUCCESS, contactToView.getName().fullName),
                contactToView);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewContactCommand)) {
            return false;
        }

        ViewContactCommand otherViewContactCommand = (ViewContactCommand) other;
        return index.equals(otherViewContactCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
