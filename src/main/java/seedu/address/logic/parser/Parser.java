package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput) throws ParseException;

    /**
     * Checks whether a given string can be parsed as an integer.
     *
     * @param str String to be checked.
     * @return true if the string represents an integer, false otherwise.
     */
    static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether all strings in a given string array can all be parsed as an integer.
     *
     * @param strings Array of strings to be checked.
     * @return true only if all strings in string represents an integer.
     */
    static boolean isIntegerArray(String[] strings) {
        for (String string : strings) {
            if (!isInteger(string)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Replaces segments in inputString that matches a string in targetStrings with replacementString, in order of
     * how it was ordered in targetStrings.
     * @param inputString String to have its segments replaced.
     * @param targetStrings Segments to replace with replacementString.
     * @param replacementString String to replace segments in inputString with.
     * @return Processed string with its internal segments replaced.
     */
    static String replaceStringWithArraySelection(
            String inputString, String[] targetStrings, String replacementString) {
        String outputString = inputString;
        for (int i = 0; i < targetStrings.length; i++) {
            outputString = outputString.replace(targetStrings[i], replacementString);
        }
        return outputString;
    }
}
