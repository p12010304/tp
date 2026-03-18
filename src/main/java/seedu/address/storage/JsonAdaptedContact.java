package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.LastContacted;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Note;
import seedu.address.model.contact.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Contact}.
 */
class JsonAdaptedContact {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Contact's %s field is missing!";

    private final String id;
    private final String name;
    private final Optional<String> phone;
    private final Optional<String> email;
    private final Optional<String> address;
    private final Optional<String> lastContacted;
    private final List<String> notes = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedContact} with the given contact details.
     */
    @JsonCreator
    public JsonAdaptedContact(@JsonProperty("id") String id, @JsonProperty("name") String name,
            @JsonProperty("phone") Optional<String> phone,
            @JsonProperty("email") Optional<String> email, @JsonProperty("address") Optional<String> address,
            @JsonProperty("lastContacted") Optional<String> lastContacted,
            @JsonProperty("notes") Object notes, @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.lastContacted = lastContacted == null ? Optional.empty() : lastContacted;
        this.notes.addAll(parseNotes(notes));
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Contact} into this class for Jackson use.
     */
    public JsonAdaptedContact(Contact source) {
        id = source.getId().toString();
        name = source.getName().fullName;
        phone = source.getPhone().map(phone -> phone.value);
        email = source.getEmail().map(email -> email.value);
        address = source.getAddress().map(address -> address.value);
        lastContacted = source.getLastContacted().map(LastContacted::toString);
        notes.addAll(source.getNotes().stream().map(Note::toJsonString).collect(Collectors.toList()));
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Parses notes from legacy and current JSON shapes.
     * Supports arrays (current), and empty/non-empty strings (legacy).
     */
    private static List<String> parseNotes(Object notesValue) {
        List<String> parsedNotes = new ArrayList<>();
        if (notesValue == null) {
            return parsedNotes;
        }
        if (notesValue instanceof String) {
            String note = ((String) notesValue).trim();
            if (!note.isEmpty()) {
                parsedNotes.add(note);
            }
            return parsedNotes;
        }
        if (notesValue instanceof List<?>) {
            for (Object noteObject : (List<?>) notesValue) {
                if (noteObject != null) {
                    parsedNotes.add(noteObject.toString());
                }
            }
        }
        return parsedNotes;
    }

    /**
     * Converts this Jackson-friendly adapted contact object into the model's {@code Contact} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact.
     */
    public Contact toModelType() throws IllegalValueException {
        final List<Tag> contactTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            contactTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (phone.isPresent() && !Phone.isValidPhone(phone.get())) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Optional<Phone> modelPhone = phone.map(phone -> new Phone(phone));

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (email.isPresent() && !Email.isValidEmail(email.get())) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Optional<Email> modelEmail = email.map(email -> new Email(email));

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (address.isPresent() && !Address.isValidAddress(address.get())) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Optional<Address> modelAddress = address.map(address -> new Address(address));

        if (lastContacted.isPresent() && !LastContacted.isValidLastContacted(lastContacted.get())) {
            throw new IllegalValueException(LastContacted.MESSAGE_CONSTRAINTS);
        }
        final Optional<LastContacted> modelLastContacted = lastContacted.map(LastContacted::new);

        final List<Note> modelNotes = notes.stream().map(Note::fromJsonString).collect(Collectors.toList());

        final Set<Tag> modelTags = new HashSet<>(contactTags);
        final UUID modelId = (id != null) ? UUID.fromString(id) : UUID.randomUUID();
        return new Contact(
                modelId, modelName, modelPhone, modelEmail, modelAddress, modelLastContacted, modelNotes, modelTags);
    }
}
