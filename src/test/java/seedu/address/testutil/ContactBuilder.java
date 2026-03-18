package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.LastContacted;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Note;
import seedu.address.model.contact.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Contact objects.
 */
public class ContactBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private UUID id;
    private Name name;
    private Optional<Phone> phone;
    private Optional<Email> email;
    private Optional<Address> address;
    private Optional<LastContacted> lastContacted;
    private List<Note> notes;
    private Set<Tag> tags;

    /**
     * Creates a {@code ContactBuilder} with the default details.
     */
    public ContactBuilder() {
        id = UUID.randomUUID();
        name = new Name(DEFAULT_NAME);
        phone = Optional.of(new Phone(DEFAULT_PHONE));
        email = Optional.of(new Email(DEFAULT_EMAIL));
        address = Optional.of(new Address(DEFAULT_ADDRESS));
        lastContacted = Optional.empty();
        notes = new ArrayList<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the ContactBuilder with the data of {@code contactToCopy}.
     */
    public ContactBuilder(Contact contactToCopy) {
        id = contactToCopy.getId();
        name = contactToCopy.getName();
        phone = contactToCopy.getPhone();
        email = contactToCopy.getEmail();
        address = contactToCopy.getAddress();
        lastContacted = contactToCopy.getLastContacted();
        notes = contactToCopy.getNotes();
        tags = new HashSet<>(contactToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Contact} that we are building.
     */
    public ContactBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Contact} that we are building.
     */
    public ContactBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Contact} that we are building.
     */
    public ContactBuilder withAddress(String address) {
        this.address = address != null ? Optional.of(new Address(address)) : Optional.empty();
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Contact} that we are building.
     */
    public ContactBuilder withPhone(String phone) {
        this.phone = phone != null ? Optional.of(new Phone(phone)) : Optional.empty();
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Contact} that we are building.
     */
    public ContactBuilder withEmail(String email) {
        this.email = email != null ? Optional.of(new Email(email)) : Optional.empty();
        return this;
    }

    /**
     * Sets the {@code Notes} of the {@code Contact} that we are building.
     */
    public ContactBuilder withNotes(String ... notes) {
        this.notes = SampleDataUtil.getNoteList(notes);
        return this;
    }

    /**
     * Sets the {@code LastContacted} of the {@code Contact} that we are building.
     */
    public ContactBuilder withLastContacted(String lastContacted) {
        this.lastContacted = lastContacted != null ? Optional.of(new LastContacted(lastContacted)) : Optional.empty();
        return this;
    }

    public Contact build() {
        return new Contact(id, name, phone, email, address, lastContacted, notes, tags);
    }

}
