package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Note;

/**
 * Changes the notes of an existing contact in the address book.
 */
public class NoteAddCommand extends NoteCommand {

    public static final String MESSAGE_ADD_NOTES_SUCCESS = "Added notes to Contact: %1$s";

    private final Index index;
    private final Note note;

    /**
     * @param index of the contact in the displayed contact list to edit the notes
     * @param note of the contact to be updated to
     */
    public NoteAddCommand(Index index, Note note) {
        requireAllNonNull(index, note);

        this.index = index;
        this.note = note;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Contact> lastShownList = model.getDisplayedContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToEdit = lastShownList.get(index.getZeroBased());
        List<Note> newNotes = new ArrayList<>(contactToEdit.getNotes());
        newNotes.add(note);

        Contact editedContact = new Contact(contactToEdit.getId(), contactToEdit.getName(),
                contactToEdit.getPhone(), contactToEdit.getEmail(),
                contactToEdit.getAddress(), contactToEdit.getLastContacted(),
                newNotes, contactToEdit.getTags());

        model.setContact(contactToEdit, editedContact);
        model.resetDisplayedContactList();

        String feedback = generateSuccessMessage(editedContact);
        model.saveSnapshot(feedback);
        return new CommandResult(feedback);
    }

    /**
     * Generates a command execution success message based on whether the notes are added to or removed from
     * {@code contactToEdit}.
     */
    private String generateSuccessMessage(Contact contactToEdit) {
        return String.format(MESSAGE_ADD_NOTES_SUCCESS, Messages.format(contactToEdit));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteAddCommand)) {
            return false;
        }

        // state check
        NoteAddCommand e = (NoteAddCommand) other;
        return index.equals(e.index)
                && note.equals(e.note);
    }
}
