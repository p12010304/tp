package seedu.address.model.contact;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
