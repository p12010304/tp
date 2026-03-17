package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;

import java.time.LocalDateTime;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.TimePointParser;
import seedu.address.model.timepoint.TimePoint;

/**
 * Represents a Contact's notes in the address book, may contain a {@code TimePoint} to act as a reminder.
 * Guarantees: immutable; is always valid
 */
public class Note {

    //Temporarily placed here for convenience, should be moved into
    //UserPrefs to allow users to set their own reminder periods
    public static final int DUE_PERIOD_DAYS = 7;

    public final String value;
    public final TimePoint timePoint;

    /**
     * Constructs a {@code Note}.
     *
     * @param note A valid note.
     */
    public Note(String note) {
        requireNonNull(note);
        value = note;
        this.timePoint = null;
    }

    /**
     * Constructs a {@code Note} with a {@code TimePoint} to act as a reminder.
     *
     * @param note A valid note.
     */
    public Note(String note, TimePoint timePoint) {
        requireNonNull(note);
        value = note;
        this.timePoint = timePoint;
    }

    /**
     * Outputs a Json formatted string to represent this note.
     */
    public String toJsonString() {
        return value + ((timePoint == null) ? "" : " on/" + timePoint.toString());
    }

    /**
     * Creates a Note from a Json formatted string.
     */
    public static Note fromJsonString(String string) {
        requireNonNull(string);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(string, PREFIX_ON);
        if (argMultimap.getValue(PREFIX_ON).isPresent()) {
            return new Note(
                    argMultimap.getPreamble(),
                    TimePointParser.toTimePoint(argMultimap.getValue(PREFIX_ON).get()));
        }
        return new Note(string);
    }

    public boolean isReminder() {
        return timePoint != null;
    }

    /**
     * Checks if this note is a reminder that is due in {@code DUE_PERIOD_DAYS} number of days.
     */
    public boolean hasDueReminder() {
        if (timePoint == null) {
            return false;
        }
        TimePoint cutOffTime = TimePoint.of(LocalDateTime.now().plusDays(DUE_PERIOD_DAYS));
        TimePoint nowTime = TimePoint.of(LocalDateTime.now());
        return timePoint.isBefore(cutOffTime) && timePoint.isAfter(nowTime);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Note && value.equals(((Note) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
