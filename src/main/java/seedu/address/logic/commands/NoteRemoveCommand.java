package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Note;

/**
 * Remove lines of notes from an existing contact in the address book.
 */
public class NoteRemoveCommand extends NoteCommand {

    public static final String MESSAGE_REMOVE_NOTES_SUCCESS = "Removed notes from Contact: %1$s";

    private final Index index;
    private final int numLines;

    /**
     * @param index    Index of the contact in the filtered contact list.
     * @param numLines How many lines of notes to remove.
     */
    public NoteRemoveCommand(Index index, int numLines) {
        requireAllNonNull(index);

        this.index = index;
        this.numLines = numLines;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Contact> lastShownList = model.getFilteredContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToEdit = lastShownList.get(index.getZeroBased());

        List<Note> newNotes = new ArrayList<>(contactToEdit.getNotes());

        int numExistingLines = newNotes.size();
        newNotes = newNotes.subList(Math.min(numLines, numExistingLines), numExistingLines);

        Contact editedContact = new Contact(contactToEdit.getName(), contactToEdit.getPhone(), contactToEdit.getEmail(),
            contactToEdit.getAddress(), newNotes, contactToEdit.getTags());

        model.setContact(contactToEdit, editedContact);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);

        return new CommandResult(generateSuccessMessage(editedContact));
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
        if (!(other instanceof NoteRemoveCommand)) {
            return false;
        }

        // state check
        NoteRemoveCommand e = (NoteRemoveCommand) other;
        return index.equals(e.index)
            && numLines == e.numLines;
    }
}
