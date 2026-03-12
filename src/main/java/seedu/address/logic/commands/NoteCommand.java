package seedu.address.logic.commands;

public abstract class NoteCommand extends Command {
    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the notes of the contact identified "
            + "by the index number used in the last contact listing. "
            + "Existing notes will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[NOTES]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "Likes to swim.";
}
