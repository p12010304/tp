package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.ConjunctiveContactPredicateSet;
import seedu.address.model.contact.Contact;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Checks that argument(s) are provided.
        if (args.isBlank()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        // Checks that all search phrases are non-empty.
        validateNonEmptyPhrases(argMultimap);
        validateNonEmptyPhrases(argMultimap);

        ConjunctiveContactPredicateSet cumulativePredicate = makeCumulativePredicate(argMultimap);

        return new FindCommand(cumulativePredicate);
    }

    /**
     * Constructs a cumulative predicate that tests if a contact matches any of the
     * search phrases provided by the user.
     * <br>
     * Notably, tag search phrases are treated as exact matches, while prefixed
     * search phrases are treated as partial matches.
     *
     * @param argMultimap
     * @return the cumulative predicate
     */
    private ConjunctiveContactPredicateSet makeCumulativePredicate(ArgumentMultimap argMultimap) {
        ConjunctiveContactPredicateSet cumulativePredicate = new ConjunctiveContactPredicateSet();
        splitKeywords(argMultimap.getPreamble()).forEach(
                        keyword -> cumulativePredicate.addPredicate(contact -> contact.contains(keyword)));
        argMultimap.getAllValues(PREFIX_NAME).forEach(
                keyword -> cumulativePredicate.addPredicate(contact -> contact.containsInName(keyword)));
        argMultimap.getAllValues(PREFIX_PHONE).forEach(
                keyword -> cumulativePredicate.addPredicate(contact -> contact.containsInPhone(keyword)));
        argMultimap.getAllValues(PREFIX_EMAIL).forEach(
                keyword -> cumulativePredicate.addPredicate(contact -> contact.containsInEmail(keyword)));
        argMultimap.getAllValues(PREFIX_ADDRESS).forEach(
                keyword -> cumulativePredicate.addPredicate(contact -> contact.containsInAddress(keyword)));
        argMultimap.getAllValues(PREFIX_TAG).forEach(
                keyword -> cumulativePredicate.addPredicate((Contact contact) -> contact.hasTag(keyword)));
        return cumulativePredicate;
    }

    /**
     * Checks that all search phrases are non-empty. Throws a ParseException if any
     * of the search phrases is empty or blank.
     *
     * @param argMultimap
     * @throws ParseException
     */
    private void validateNonEmptyPhrases(ArgumentMultimap argMultimap) throws ParseException {
        boolean isNameValid = argMultimap.getAllValues(PREFIX_NAME).stream()
                .allMatch(keyword -> !keyword.isBlank());
        boolean isPhoneValid = argMultimap.getAllValues(PREFIX_PHONE).stream()
                .allMatch(keyword -> !keyword.isBlank());
        boolean isEmailValid = argMultimap.getAllValues(PREFIX_EMAIL).stream()
                .allMatch(keyword -> !keyword.isBlank());
        boolean isAddressValid = argMultimap.getAllValues(PREFIX_ADDRESS).stream()
                .allMatch(keyword -> !keyword.isBlank());
        boolean isTagValid = argMultimap.getAllValues(PREFIX_TAG).stream().allMatch(keyword -> !keyword.isBlank());

        if (!isNameValid || !isPhoneValid || !isEmailValid || !isAddressValid || !isTagValid) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Splits a raw keyword string by whitespace and drops empty tokens.
     */
    private List<String> splitKeywords(String rawKeywords) {
        return Arrays.stream(rawKeywords.trim().split("\\s+"))
                .filter(keyword -> !keyword.isBlank())
                .collect(Collectors.toList());
    }
}
