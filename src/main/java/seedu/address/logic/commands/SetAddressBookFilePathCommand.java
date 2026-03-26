package seedu.address.logic.commands;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;

/**
 * Changes the address book file path.
 */
public class SetAddressBookFilePathCommand extends SetCommand {
    public static final String MESSAGE_SUCCESS = "Using data file: %1$s";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final String fileName;

    /**
     * @param fileName Name of the address book file to change to.
     */
    public SetAddressBookFilePathCommand(String fileName) {
        requireAllNonNull(fileName);
        checkArgument(UserPrefs.isValidFileName(fileName), UserPrefs.FILENAME_CONSTRAINTS_MESSAGE);
        this.fileName = fileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Path filePath = UserPrefs.formatAddressBookFilePath(fileName);
        model.setAddressBookFilePath(filePath);
        model.resetDisplayedContactList();

        logger.info("Using data file: " + filePath);

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook data;
        try {
            addressBookOptional = new JsonAddressBookStorage(filePath).readAddressBook();
            if (addressBookOptional.isEmpty()) {
                logger.info("Creating a new data file " + filePath + " with empty AddressBook.");
            }
            data = addressBookOptional.orElseGet(AddressBook::new);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + filePath + " could not be loaded."
                    + " Will be starting with an empty AddressBook.");
            data = new AddressBook();
        }
        model.setAddressBook(data);

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
