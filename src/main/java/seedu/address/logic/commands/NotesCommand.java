package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Notes;

/**
 * Changes the notes of an existing contact in the address book.
 */
public class NotesCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the notes of the contact identified "
            + "by the index number used in the last contact listing. "
            + "Existing notes will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[NOTES]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "Likes to swim.";

    public static final String MESSAGE_ADD_NOTES_SUCCESS = "Added notes to Contact: %1$s";
    public static final String MESSAGE_DELETE_NOTES_SUCCESS = "Removed notes from Contact: %1$s";

    private final Index index;
    private final Notes notes;

    /**
     * @param index of the contact in the filtered contact list to edit the notes
     * @param notes of the contact to be updated to
     */
    public NotesCommand(Index index, Notes notes) {
        requireAllNonNull(index, notes);

        this.index = index;
        this.notes = notes;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Contact> lastShownList = model.getFilteredContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToEdit = lastShownList.get(index.getZeroBased());
        Contact editedContact = new Contact(contactToEdit.getName(), contactToEdit.getPhone(), contactToEdit.getEmail(),
                contactToEdit.getAddress(), notes, contactToEdit.getTags());

        model.setContact(contactToEdit, editedContact);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);

        return new CommandResult(generateSuccessMessage(editedContact));
    }

    /**
     * Generates a command execution success message based on whether the notes are added to or removed from
     * {@code contactToEdit}.
     */
    private String generateSuccessMessage(Contact contactToEdit) {
        String message = !notes.value.isEmpty() ? MESSAGE_ADD_NOTES_SUCCESS : MESSAGE_DELETE_NOTES_SUCCESS;
        return String.format(message, contactToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NotesCommand)) {
            return false;
        }

        // state check
        NotesCommand e = (NotesCommand) other;
        return index.equals(e.index)
                && notes.equals(e.notes);
    }
}
