package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.contact.Contact;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a contact with a similar identity as {@code contact} exists in the address book.
     */
    boolean hasSimilarContact(Contact contact);

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the address book.
     */
    boolean hasContact(Contact contact);

    /**
     * Deletes the given contact.
     * The contact must exist in the address book.
     */
    void deleteContact(Contact target);

    /**
     * Adds the given contact.
     * {@code contact} must not already exist in the address book.
     */
    void addContact(Contact contact);

    /**
     * Replaces the given contact {@code target} with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contact identity of {@code editedContact} must not be the same as another existing contact in the address
     * book.
     */
    void setContact(Contact target, Contact editedContact);

    /** Returns an unmodifiable view of the displayed contact list */
    ObservableList<Contact> getDisplayedContactList();

    /**
     * Resets the displayed contact list to show all contacts in the address book order.
     */
    void resetDisplayedContactList();

    /**
     * Updates the filter of the displayed contact list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void filterDisplayedContactList(Predicate<Contact> predicate);

    /**
     * Updates the order of the displayed contact list to sort by the given {@code comparator}.
     * @throws NullPointerException if {@code comparator} is null.
     */
    void sortDisplayedContactList(Comparator<Contact> comparator);

    /**
     * Returns a {@code Snapshot} of the current model for undo/redo features.
     */
    public Snapshot getSnapshot();

    /**
     * Saves a {@code Snapshot} internally for undo/redo features
     * @param description Description of the snapshot.
     */
    public void saveSnapshot(String description);

    /**
     * Moves the model forward or backwards by the indicated number of steps.
     * @param offset Number of snapshots to move the model by.
     * @return Name of the snapshot model successfully moved to.
     */
    public String moveSnapshot(int offset) throws IndexOutOfBoundsException;
}
