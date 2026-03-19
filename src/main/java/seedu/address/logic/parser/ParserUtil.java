package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.LastContacted;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.tag.RankedTag;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String[] splitName = name.trim().split("\\s+");
        String titledName = Arrays.stream(splitName)
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
        if (!Name.isValidName(titledName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(titledName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.replaceAll("\\s+", "");
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim().replaceAll("\\s+", " ");
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String lastContacted} into a {@code LastContacted}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code lastContacted} is invalid.
     */
    public static LastContacted parseLastContacted(String lastContacted) throws ParseException {
        requireNonNull(lastContacted);
        String trimmedLastContacted = lastContacted.trim();
        if (!LastContacted.isValidLastContacted(trimmedLastContacted)) {
            throw new ParseException(LastContacted.MESSAGE_CONSTRAINTS);
        }
        return new LastContacted(trimmedLastContacted);
    }

    /**
     * Parses a {@code String tagName} and a {@code String tagValue} into a {@code RankedTag}.
     * @param tagName The name of the tag.
     * @param tagValue The value for the tag.
     */
    public static RankedTag parseRankedTag(String tagName, String tagValue) throws ParseException {
        requireNonNull(tagName);
        requireNonNull(tagValue);
        String trimmedTagName = tagName.trim();
        String trimmedTagValue = tagValue.trim();

        if (!RankedTag.isValidTagName(trimmedTagName)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }

        if (!RankedTag.isValidTagValue(trimmedTagValue)) {
            throw new ParseException(RankedTag.MESSAGE_CONSTRAINTS);
        }

        return new RankedTag(trimmedTagName, trimmedTagValue);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();

        // Handle ranked tags
        if (trimmedTag.contains(":")) {
            String[] tagArgs = trimmedTag.split(":", 2);
            return parseRankedTag(tagArgs[0], tagArgs[1]);
        }

        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }

        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
