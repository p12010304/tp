package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.util.Pair;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.contact.Contact;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    public static final String UNDO_LIMIT_MESSAGE = "Model already at earliest snapshot.";
    public static final String REDO_LIMIT_MESSAGE = "Model already at newest snapshot.";

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final List<Pair<String, Snapshot>> snapshots;

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Contact> filteredContacts;

    private Predicate<Contact> filterPredicate;
    private int snapshotPosition;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredContacts = new FilteredList<>(this.addressBook.getContactList());
        filterPredicate = PREDICATE_SHOW_ALL_CONTACTS;

        snapshots = new ArrayList<>();
        snapshots.add(new Pair<String, Snapshot>("", getSnapshot()));
        snapshotPosition = 0;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasSimilarContact(Contact contact) {
        requireNonNull(contact);
        return addressBook.hasSimilarContact(contact);
    }

    @Override
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return addressBook.hasContact(contact);
    }

    @Override
    public void deleteContact(Contact target) {
        addressBook.removeContact(target);
    }

    @Override
    public void addContact(Contact contact) {
        updateFilteredContactList(contact::isSimilarContact);
        addressBook.addContact(contact);
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);

        addressBook.setContact(target, editedContact);
    }

    //=========== Filtered Contact List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Contact} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return filteredContacts;
    }

    @Override
    public void updateFilteredContactList(Predicate<Contact> predicate) {
        requireNonNull(predicate);
        filteredContacts.setPredicate(predicate);
        filterPredicate = predicate;
    }

    //=========== Snapshot ================================================================================

    /**
     * Returns a {@code Snapshot} of the model for undo/redo features.
     */
    @Override
    public Snapshot getSnapshot() {
        ArrayList<Contact> copyContacts = new ArrayList<>();
        getAddressBook().getContactList().forEach(contact -> copyContacts.add(contact.copy()));
        return new Snapshot(copyContacts, getUserPrefs(), filterPredicate);
    }

    /**
     * Copies data from a {@code Snapshot} for undo/redo features.
     */
    private void copySnapshot(Snapshot snapshot) {
        requireNonNull(snapshot);
        addressBook.setContacts(snapshot.contactList());
        setUserPrefs(snapshot.userPrefs());
        filteredContacts.setPredicate(snapshot.filterPredicate());
        filterPredicate = snapshot.filterPredicate();
    }

    /**
     * Saves a {@code Snapshot} internally for undo/redo features
     * @param snapshotName Name of the snapshot.
     */
    public void saveSnapshot(String snapshotName) {
        snapshotPosition++;
        if (snapshotPosition < snapshots.size()) {
            snapshots.subList(snapshotPosition, snapshots.size()).clear();
        }
        snapshots.add(snapshotPosition, new Pair<String, Snapshot>(snapshotName, getSnapshot()));
    }

    /**
     * Moves the model forward or backwards by the indicated number of steps.
     * @param stepsToMove Number of snapshots to move the model by.
     * @return Name of the snapshot model successfully moved to.
     */
    public String changeSnapshot(int stepsToMove) throws CommandException {
        assert(stepsToMove != 0);
        if (stepsToMove > 0) {
            int snapshotsToShift = Math.min(snapshots.size() - snapshotPosition - 1, stepsToMove);
            if (snapshotsToShift > 0) {
                snapshotPosition += snapshotsToShift;
                copySnapshot(snapshots.get(snapshotPosition).getValue());
                return snapshots.get(snapshotPosition).getKey();
            } else {
                throw new CommandException(REDO_LIMIT_MESSAGE);
            }
        } else {
            int snapshotsToShift = Math.min(snapshotPosition, -stepsToMove);
            if (snapshotsToShift > 0) {
                snapshotPosition -= snapshotsToShift;
                copySnapshot(snapshots.get(snapshotPosition).getValue());
                return snapshots.get(snapshotPosition + 1).getKey();
            } else {
                throw new CommandException(UNDO_LIMIT_MESSAGE);
            }
        }
    }
    //=========== Override ================================================================================

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredContacts.equals(otherModelManager.filteredContacts);
    }

}
