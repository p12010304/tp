package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Note;

/**
 * Deletes a contact identified using its displayed index from the address book.
 */
public class DeleteContactCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted contact: %1$s";

    private final Index index;

    /**
     * @param index of the contact in the displayed contact list to delete
     */
    public DeleteContactCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getDisplayedContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.getIndexOutOfRangeMessage(lastShownList.size()));
        }

        Contact contactToDelete = lastShownList.get(index.getZeroBased());
        UUID deletedId = contactToDelete.getId();
        String deletedName = contactToDelete.getName().fullName;

        // Dereference notes in all other contacts that reference the deleted contact
        for (Contact c : new ArrayList<>(model.getAddressBook().getContactList())) {
            if (c.getId().equals(deletedId)) {
                continue;
            }
            List<Note> updatedNotes = c.getNotes().stream()
                    .map(n -> n.dereferenceContact(deletedId, deletedName))
                    .collect(Collectors.toList());
            if (!updatedNotes.equals(c.getNotes())) {
                Contact updatedContact = new Contact(c.getId(), c.getName(),
                        c.getPhone(), c.getEmail(), c.getAddress(),
                        c.getLastContacted(), c.getLastUpdated(),
                        updatedNotes, c.getTags());
                model.setContact(c, updatedContact);
            }
        }

        model.deleteContact(contactToDelete);

        String feedback = String.format(MESSAGE_DELETE_CONTACT_SUCCESS, Messages.format(contactToDelete));
        model.saveSnapshot(feedback);
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteContactCommand)) {
            return false;
        }

        DeleteContactCommand otherDeleteContactCommand = (DeleteContactCommand) other;
        return index.equals(otherDeleteContactCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
