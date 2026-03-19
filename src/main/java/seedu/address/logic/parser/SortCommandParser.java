package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_UPDATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactComparator;
import seedu.address.model.contact.ContactComparatorSet;
import seedu.address.model.contact.ContactTagComparator;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final Map<Prefix, ContactComparator.Field> PREFIX_FIELD_MAP = Map.of(
        PREFIX_NAME, ContactComparator.Field.NAME,
        PREFIX_PHONE, ContactComparator.Field.PHONE,
        PREFIX_EMAIL, ContactComparator.Field.EMAIL,
        PREFIX_ADDRESS, ContactComparator.Field.ADDRESS,
        PREFIX_LAST_UPDATED, ContactComparator.Field.LAST_UPDATED
    );

    /**
     * Extracts comparators from a {@code ArgumentMultimap} and combines them into a single {@code Comparator<Contact>}.
     *
     * @param argMultimap The arguments to sort by.
     * @throws ParseException If there are no valid arguments to sort by.
     */
    private static Comparator<Contact> makeCombinedComparator(ArgumentMultimap argMultimap) throws ParseException {
        ContactComparatorSet combinedComparator = new ContactComparatorSet();

        for (Prefix prefix : PREFIX_FIELD_MAP.keySet()) {
            Optional<String> value = argMultimap.getValue(prefix);

            if (value.isEmpty()) {
                continue;
            }

            combinedComparator.addComparator(new ContactComparator(
                PREFIX_FIELD_MAP.get(prefix),
                value.get().equals(SortCommand.DESCENDING_KEYWORD)
                    ? ContactComparator.Order.DESCENDING
                    : ContactComparator.Order.ASCENDING
            ));
        }

        List<String> values = argMultimap.getAllValues(PREFIX_TAG);

        if (!values.isEmpty()) {
            for (String value : values) {
                combinedComparator.addComparator(new ContactTagComparator(
                    value,
                    ContactComparator.Order.DESCENDING // TODO: Decide on format for specifying order for tag sorting
                ));
            }
        }

        if (combinedComparator.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return combinedComparator;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Checks that argument(s) are provided
        if (args.isBlank()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_LAST_UPDATED, PREFIX_TAG);

        Comparator<Contact> combinedComparator = makeCombinedComparator(argMultimap);

        return new SortCommand(combinedComparator);
    }
}
