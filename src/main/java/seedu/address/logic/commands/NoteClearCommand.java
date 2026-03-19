package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Remove lines of notes from an existing contact in the address book.
 */
public class NoteClearCommand extends NoteCommand {

    public static final String MESSAGE_REMOVE_NOTES_SUCCESS = "Removed notes from Contact: %1$s";

    private final Index index;

    /**
     * @param index Index of the contact in the displayed contact list.
     */
    public NoteClearCommand(Index index) {
        requireAllNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Contact> lastShownList = model.getDisplayedContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToEdit = lastShownList.get(index.getZeroBased());

        Contact editedContact = new Contact(contactToEdit.getId(), contactToEdit.getName(),
            contactToEdit.getPhone(), contactToEdit.getEmail(),
            contactToEdit.getAddress(), contactToEdit.getLastContacted(),
            new ArrayList<>(), contactToEdit.getTags());

        model.setContact(contactToEdit, editedContact);
        model.resetDisplayedContactList();

        String feedback = generateSuccessMessage(editedContact);
        model.saveSnapshot(feedback);
        return new CommandResult(feedback);
    }

    /**
     * Generates a command execution success message.
     * {@code contactToEdit}.
     */
    private String generateSuccessMessage(Contact contactToEdit) {
        return String.format(MESSAGE_REMOVE_NOTES_SUCCESS, Messages.format(contactToEdit));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteClearCommand)) {
            return false;
        }

        // state check
        NoteClearCommand e = (NoteClearCommand) other;
        return index.equals(e.index);
    }
}
