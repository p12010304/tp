package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.util.Pair;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
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
    private final ObservableList<Contact> displayedContacts;
    private final FilteredList<Contact> filteredContacts;
    private final SortedList<Contact> sortedContacts;

    private int snapshotPosition;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredContacts = new FilteredList<>(this.addressBook.getContactList());
        this.sortedContacts = new SortedList<>(this.filteredContacts);
        this.displayedContacts = this.sortedContacts;

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
        addressBook.addContact(contact);
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);

        addressBook.setContact(target, editedContact);
    }

    //=========== Displayed Contact List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Contact} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Contact> getDisplayedContactList() {
        return displayedContacts;
    }

    @Override
    public void resetDisplayedContactList() {
        filteredContacts.setPredicate(null);
        sortedContacts.setComparator(null);
    }

    @Override
    public void filterDisplayedContactList(Predicate<Contact> predicate) {
        requireNonNull(predicate);

        @SuppressWarnings("unchecked")
        Predicate<Contact> currentPredicate = (Predicate<Contact>) filteredContacts.getPredicate();
        filteredContacts.setPredicate(currentPredicate == null ? predicate : currentPredicate.and(predicate));
        final FilteredList<Contact> filteredContacts = new FilteredList<>(displayedContacts);
        filteredContacts.setPredicate(predicate);
    }

    @Override
    public void sortDisplayedContactList(Comparator<Contact> comparator) {
        requireNonNull(comparator);

        @SuppressWarnings("unchecked")
        Comparator<Contact> currentComparator = (Comparator<Contact>) sortedContacts.getComparator();
        sortedContacts
                .setComparator(currentComparator == null ? comparator : currentComparator.thenComparing(comparator));
    }

    //=========== Snapshot ================================================================================

    /**
     * Returns a {@code Snapshot} of the model for undo/redo features.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Snapshot getSnapshot() {
        ArrayList<Contact> copyContacts = new ArrayList<>();
        getAddressBook().getContactList().forEach(contact -> copyContacts.add(contact.copy()));

        return new Snapshot(
                copyContacts,
                getUserPrefs(),
                (Predicate<Contact>) filteredContacts.getPredicate(),
                (Comparator<Contact>) sortedContacts.getComparator());
    }

    /**
     * Copies data from a {@code Snapshot} for undo/redo features.
     */
    private void copySnapshot(Snapshot snapshot) {
        requireNonNull(snapshot);
        addressBook.setContacts(snapshot.contactList());
        setUserPrefs(snapshot.userPrefs());
        resetDisplayedContactList();
        if (snapshot.filterPredicate() != null) {
            filterDisplayedContactList(snapshot.filterPredicate());
        }
        if (snapshot.sortComparator() != null) {
            sortDisplayedContactList(snapshot.sortComparator());
        }
    }

    /**
     * Saves a {@code Snapshot} internally for undo/redo features
     * @param description Name of the snapshot.
     */
    @Override
    public void saveSnapshot(String description) {
        snapshotPosition++;
        if (snapshotPosition < snapshots.size()) {
            snapshots.subList(snapshotPosition, snapshots.size()).clear();
        }
        snapshots.add(new Pair<String, Snapshot>(description, getSnapshot()));
    }

    /**
     * Moves the model forward or backwards by the indicated number of steps.
     * @param offset Number of snapshots to move the model by.
     * @return Name of the snapshot model successfully moved to.
     */
    @Override
    public String moveSnapshot(int offset) throws IndexOutOfBoundsException {
        assert(offset != 0);
        if (offset > 0) {
            int snapshotsToShift = Math.min(snapshots.size() - snapshotPosition - 1, offset);
            if (snapshotsToShift > 0) {
                snapshotPosition += snapshotsToShift;
                copySnapshot(snapshots.get(snapshotPosition).getValue());
                return snapshots.get(snapshotPosition).getKey();
            } else {
                throw new IndexOutOfBoundsException(REDO_LIMIT_MESSAGE);
            }
        } else {
            int snapshotsToShift = Math.min(snapshotPosition, -offset);
            if (snapshotsToShift > 0) {
                snapshotPosition -= snapshotsToShift;
                copySnapshot(snapshots.get(snapshotPosition).getValue());
                return snapshots.get(snapshotPosition + 1).getKey();
            } else {
                throw new IndexOutOfBoundsException(UNDO_LIMIT_MESSAGE);
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
                && displayedContacts.equals(otherModelManager.displayedContacts);
    }

}
