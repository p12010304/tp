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
 * Remove a specific note from an existing contact in the address book.
 */
public class NoteRemoveCommand extends NoteCommand {

    public static final String MESSAGE_REMOVE_NOTE_SUCCESS = "Removed note from Contact: %1$s";
    public static final String MESSAGE_INVALID_NOTE_INDEX = "The note index provided is invalid";

    private final Index contactIndex;
    private final Index noteIndex;

    /**
     * Creates a NoteRemoveCommand to remove a specific note from an existing contact in the address book.
     *
     * @param contactIndex Index of the contact in the displayed contact list.
     * @param noteIndex    The index of the note (for the contact).
     */
    public NoteRemoveCommand(Index contactIndex, Index noteIndex) {
        requireAllNonNull(contactIndex, noteIndex);

        this.contactIndex = contactIndex;
        this.noteIndex = noteIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Contact> lastShownList = model.getDisplayedContactList();

        if (contactIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToEdit = lastShownList.get(contactIndex.getZeroBased());

        List<Note> newNotes = new ArrayList<>(contactToEdit.getNotes());

        if (noteIndex.getZeroBased() >= contactToEdit.getNotes().size()) {
            throw new CommandException(MESSAGE_INVALID_NOTE_INDEX);
        }

        newNotes.remove(noteIndex.getZeroBased());

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
     * Generates a command execution success message.
     * {@code contactToEdit}.
     */
    private String generateSuccessMessage(Contact contactToEdit) {
        return String.format(MESSAGE_REMOVE_NOTE_SUCCESS, Messages.format(contactToEdit));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NoteRemoveCommand e)) {
            return false;
        }

        // state check
        return contactIndex.equals(e.contactIndex)
                && noteIndex.equals(e.noteIndex);
    }
}
