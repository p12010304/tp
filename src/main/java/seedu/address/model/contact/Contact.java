package seedu.address.model.contact;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Contact in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Contact {

    // Identity fields
    private final Name name;
    private final Optional<Phone> phone;
    private final Optional<Email> email;

    // Data fields
    private final Optional<Address> address;
    private final List<Note> notes;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Contact(
            Name name, Optional<Phone> phone, Optional<Email> email,
            Optional<Address> address, List<Note> notes, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.notes = List.copyOf(notes);
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Optional<Phone> getPhone() {
        return phone;
    }

    public Optional<Email> getEmail() {
        return email;
    }

    public Optional<Address> getAddress() {
        return address;
    }

    /**
     * Returns a {@code List} containing every {@code Note} in this contact.
     * Use {@code getNotesString()} instead for the notes in string format.
     */
    public List<Note> getNotes() {
        return Collections.unmodifiableList(notes);
    }

    /**
     * Returns the notes formatted as a string, with every note separated by line break.
     */
    public String getNotesString() {
        return notes.stream().map(note -> note.value).collect(Collectors.joining("\n"));
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if this contact contains the given string in any of its fields.
     */
    public boolean contains(String string) {
        return containsInName(string)
                || containsInPhone(string)
                || containsInEmail(string)
                || containsInAddress(string)
                || containsInNotes(string)
                || containsInTags(string);
    }

    /**
     * Returns true if the {@code Name} of this contact contains the given string.
     * @param string {@code String} to check against this contact.
     */
    public boolean containsInName(String string) {
        if (string.isEmpty()) {
            return true;
        }
        return name.fullName.toLowerCase(Locale.ROOT).contains(string.toLowerCase(Locale.ROOT));
    }

    /**
     * Returns true if the {@code Phone} of this contact contains the given string.
     * @param string {@code String} to check against this contact.
     */
    public boolean containsInPhone(String string) {
        if (string.isEmpty()) {
            return true;
        }
        return phone.map(
                phone -> phone.value.toLowerCase(Locale.ROOT).contains(string.toLowerCase(Locale.ROOT))).orElse(false);
    }

    /**
     * Returns true if the {@code Email} of this contact contains the given string.
     * @param string {@code String} to check against this contact.
     */
    public boolean containsInEmail(String string) {
        if (string.isEmpty()) {
            return true;
        }
        return email.map(
                email -> email.value.toLowerCase(Locale.ROOT).contains(string.toLowerCase(Locale.ROOT))).orElse(false);
    }

    /**
     * Returns true if the {@code Notes} of this contact contains the given string.
     * @param string {@code String} to check against this contact.
     */
    public boolean containsInNotes(String string) {
        if (string.isEmpty()) {
            return true;
        }
        return getNotesString().toLowerCase(Locale.ROOT).contains(string.toLowerCase(Locale.ROOT));
    }

    /**
     * Returns true if at least one {@code Tag} of this contact contains the given string.
     * @param string {@code String} to check against this contact.
     */
    public boolean containsInTags(String string) {
        if (string.isEmpty()) {
            return true;
        }
        return tags.stream().anyMatch(
                tag -> tag.tagName.toLowerCase(Locale.ROOT).contains(string.toLowerCase(Locale.ROOT)));
    }

    /**
     * Returns true if the {@code Tags} of this contact contains the given string.
     * This contact must contain a {@code Tag} that matches the given {@code String} exactly.
     * @param string {@code String} to check against this contact.
     */
    public boolean hasTag(String string) {
        return tags.stream().anyMatch(
                tag -> tag.tagName.toLowerCase(Locale.ROOT).equals(string.toLowerCase(Locale.ROOT)));
    }

    /**
     * Returns true if the {@code Address} of this contact contains the given string.
     * @param string {@code String} to check against this contact.
     */
    public boolean containsInAddress(String string) {
        if (string.isEmpty()) {
            return true;
        }
        return address
                .map(address -> address.value.toLowerCase(Locale.ROOT).contains(string.toLowerCase(Locale.ROOT)))
                .orElse(false);
    }

    /**
     * Checks if this contact contain reminders that are due in {@code DUE_PERIOD_DAYS} number of days.
     */
    public boolean hasDueReminders() {
        return notes.stream().anyMatch(Note::hasDueReminder);
    }

    /**
     * Returns a {@code List} containing every {@code Note} in this contact that is a reminder.
     */
    public List<Note> getReminders() {
        return notes.stream().filter(Note::isReminder).collect(Collectors.toList());
    }

    /**
     * Returns a {@code List} containing every {@code Note} in this contact that is a reminder that is due.
     */
    public List<Note> getDueReminders() {
        return notes.stream().filter(Note::hasDueReminder).collect(Collectors.toList());
    }

    /**
     * Returns true if both contacts have the same name, phone number, and email,
     * given that they are non-empty.
     * This defines a weaker notion of equality between two contacts.
     */
    public boolean isSameContact(Contact otherContact) {
        if (otherContact == this) {
            return true;
        }

        if (otherContact == null) {
            return false;
        }

        return otherContact.getName().equals(getName()) && otherContact.getPhone().equals(getPhone())
                && otherContact.getEmail().equals(getEmail());
    }

    /**
     * Returns true if both contacts have the same name, phone number, or email.
     * This defines a weaker notion of equality between two contacts.
     */
    public boolean isSimilarContact(Contact otherContact) {
        if (otherContact == this) {
            return true;
        }

        if (otherContact == null) {
            return false;
        }

        boolean isEqualPhone = getPhone().flatMap(phone -> otherContact.getPhone().map(phone::equals)).orElse(false);
        boolean isEqualEmail = getEmail().flatMap(email -> otherContact.getEmail().map(email::equals)).orElse(false);

        return otherContact.getName().equals(getName()) || isEqualPhone || isEqualEmail;
    }

    /**
     * Returns true if both contacts have the same identity and data fields.
     * This defines a stronger notion of equality between two contacts.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Contact)) {
            return false;
        }

        Contact otherContact = (Contact) other;
        return name.equals(otherContact.name)
                && phone.equals(otherContact.phone)
                && email.equals(otherContact.email)
                && address.equals(otherContact.address)
                && tags.equals(otherContact.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
