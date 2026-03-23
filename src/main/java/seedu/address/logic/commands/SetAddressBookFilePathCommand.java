package seedu.address.logic.commands;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;

/**
 * Changes the address book file path.
 */
public class SetAddressBookFilePathCommand extends SetCommand {
    public static final String MESSAGE_SUCCESS = "Using data file: %1$s";
    public static final String MESSAGE_CONSTRAINTS =
            "File names should only contain alphanumeric characters and the underscore character '_'.";

    private final String fileName;

    /**
     * @param fileName Name of the address book file to change to.
     */
    public SetAddressBookFilePathCommand(String fileName) {
        requireAllNonNull(fileName);
        checkArgument(UserPrefs.isValidFileName(fileName), MESSAGE_CONSTRAINTS);
        this.fileName = fileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Path filePath = UserPrefs.formatAddressBookFilePath(fileName);
        model.setAddressBookFilePath(filePath);
        model.resetDisplayedContactList();

        String feedback = String.format(MESSAGE_SUCCESS, filePath);
        model.saveSnapshot(feedback);
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetAddressBookFilePathCommand)) {
            return false;
        }

        // state check
        SetAddressBookFilePathCommand o = (SetAddressBookFilePathCommand) other;
        return fileName.equals(o.fileName);
    }
}
