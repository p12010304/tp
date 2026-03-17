package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Note;
import seedu.address.model.contact.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final List<Note> EMPTY_NOTES = new ArrayList<>();

    public static Contact[] getSampleContacts() {
        return new Contact[] {
            new Contact(new Name("Alex Yeoh"), Optional.of(new Phone("87438807")),
                Optional.of(new Email("alexyeoh@example.com")),
                Optional.of(new Address("Blk 30 Geylang Street 29, #06-40")),
                EMPTY_NOTES,
                getTagSet("friends")),
            new Contact(new Name("Bernice Yu"), Optional.of(new Phone("99272758")),
                Optional.of(new Email("berniceyu@example.com")),
                Optional.of(new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18")),
                EMPTY_NOTES,
                getTagSet("colleagues", "friends")),
            new Contact(new Name("Charlotte Oliveiro"), Optional.of(new Phone("93210283")),
                Optional.of(new Email("charlotte@example.com")),
                Optional.of(new Address("Blk 11 Ang Mo Kio Street 74, #11-04")),
                EMPTY_NOTES,
                getTagSet("neighbours")),
            new Contact(new Name("David Li"), Optional.of(new Phone("91031282")),
                Optional.of(new Email("lidavid@example.com")),
                Optional.of(new Address("Blk 436 Serangoon Gardens Street 26, #16-43")),
                EMPTY_NOTES,
                getTagSet("family")),
            new Contact(new Name("Irfan Ibrahim"), Optional.of(new Phone("92492021")),
                Optional.of(new Email("irfan@example.com")),
                Optional.of(new Address("Blk 47 Tampines Street 20, #17-35")),
                EMPTY_NOTES,
                getTagSet("classmates")),
            new Contact(new Name("Roy Balakrishnan"), Optional.of(new Phone("92624417")),
                Optional.of(new Email("royb@example.com")),
                Optional.of(new Address("Blk 45 Aljunied Street 85, #11-31")),
                EMPTY_NOTES,
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Contact sampleContact : getSampleContacts()) {
            sampleAb.addContact(sampleContact);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a notes list containing the list of strings given.
     */
    public static List<Note> getNoteList(String... strings) {
        return Arrays.stream(strings)
                .map(Note::fromJsonString)
                .collect(Collectors.toList());
    }
}
