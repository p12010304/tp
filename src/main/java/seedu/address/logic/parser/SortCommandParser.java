package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_CONTACTED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_UPDATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Locale;
import java.util.Map;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.ContactComparator;
import seedu.address.model.contact.ContactFieldComparator;
import seedu.address.model.contact.ContactTagComparator;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private static final Map<Prefix, ContactFieldComparator.Field> PREFIX_FIELD_MAP = Map.of(
        PREFIX_NAME, ContactFieldComparator.Field.NAME,
        PREFIX_PHONE, ContactFieldComparator.Field.PHONE,
        PREFIX_EMAIL, ContactFieldComparator.Field.EMAIL,
        PREFIX_ADDRESS, ContactFieldComparator.Field.ADDRESS,
        PREFIX_LAST_CONTACTED, ContactFieldComparator.Field.LAST_CONTACTED,
        PREFIX_LAST_UPDATED, ContactFieldComparator.Field.LAST_UPDATED
    );

    private static final Map<String, ContactFieldComparator.Order> ORDER_KEYWORD_MAP = Map.of(
        SortCommand.ASCENDING_KEYWORD, ContactComparator.Order.ASCENDING,
        SortCommand.DESCENDING_KEYWORD, ContactComparator.Order.DESCENDING
    );

    /**
     * Extracts comparators from a {@code ArgumentMultimap} and combines them into a single {@code ContactComparator}.
     *
     * @param argMultimap The arguments to sort by.
     * @throws ParseException If there are no valid arguments to sort by.
     */
    private static ContactComparator makeCombinedComparator(ArgumentMultimap argMultimap) throws ParseException {
        ContactComparator combinedComparator = ContactComparator.identity();

        for (Map.Entry<Prefix, String> arg : argMultimap.getArguments()) {
            Prefix prefix = arg.getKey();
            String value = arg.getValue().toLowerCase(Locale.ROOT);

            if (PREFIX_FIELD_MAP.containsKey(prefix) && ORDER_KEYWORD_MAP.containsKey(value)) {
                ContactFieldComparator.Field field = PREFIX_FIELD_MAP.get(prefix);
                ContactFieldComparator.Order order = ORDER_KEYWORD_MAP.get(value);
                combinedComparator = combinedComparator.thenComparing(new ContactFieldComparator(field, order));
            } else if (prefix.equals(PREFIX_TAG)) {
                combinedComparator = combinedComparator.thenComparing(makeTagComparator(value));
            } else {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
            }
        }

        return combinedComparator;
    }

    private static ContactComparator makeTagComparator(String value) throws ParseException {
        String[] parts = value.split(":", 2);
        if (parts.length != 2 || !ORDER_KEYWORD_MAP.containsKey(parts[1].toLowerCase(Locale.ROOT))) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        String tagName = parts[0];
        ContactFieldComparator.Order order = ORDER_KEYWORD_MAP.get(parts[1]);
        return new ContactTagComparator(tagName, order);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Reset to default order if no arguments are provided
        if (args.isBlank()) {
            return new SortCommand();
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_LAST_CONTACTED, PREFIX_LAST_UPDATED, PREFIX_TAG);

        if (!argMultimap.getPreamble().isBlank()) {
            System.err.println("Non-empty preamble detected: '" + argMultimap.getPreamble() + "'");
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        ContactComparator combinedComparator = makeCombinedComparator(argMultimap);

        return new SortCommand(combinedComparator);
    }
}
