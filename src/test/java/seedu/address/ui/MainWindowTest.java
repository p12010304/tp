package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.ContactBuilder;

/**
 * Contains tests for {@code MainWindow} panel management logic.
 */
public class MainWindowTest extends GuiUnitTest {

    private MainWindow mainWindow;
    private StubLogic logic;

    @BeforeEach
    public void setUp() throws Exception {
        logic = new StubLogic();
        runAndWait(() -> {
            Stage stage = new Stage();
            mainWindow = new MainWindow(stage, logic);
            mainWindow.fillInnerParts();
        });
    }

    @Test
    public void executeCommand_hideContactDetail_hidesPanelAndClearsId() throws Exception {
        runAndWait(() -> {
            mainWindow.setViewedContactId(UUID.randomUUID());
            logic.setNextResult(new CommandResult("closed", false, false, null, true));
            assertDoesNotThrow(() -> mainWindow.executeCommand("close view"));
            assertNull(mainWindow.getViewedContactId());
        });
    }

    @Test
    public void executeCommand_showContactDetail_showsPanelAndSetsId() throws Exception {
        runAndWait(() -> {
            Contact contact = new ContactBuilder().withName("Test").build();
            logic.setNextResult(new CommandResult("viewed", false, false, contact, false));
            assertDoesNotThrow(() -> mainWindow.executeCommand("view 1"));
            assertNotNull(mainWindow.getViewedContactId());
        });
    }

    @Test
    public void executeCommand_regularCommandWithViewedContact_refreshesPanel() throws Exception {
        runAndWait(() -> {
            Contact contact = new ContactBuilder().withName("Test").build();
            logic.getContacts().add(contact);
            mainWindow.setViewedContactId(contact.getId());
            logic.setNextResult(new CommandResult("listed"));
            assertDoesNotThrow(() -> mainWindow.executeCommand("list"));
            assertNotNull(mainWindow.getViewedContactId());
        });
    }

    @Test
    public void executeCommand_regularCommandWithDeletedContact_hidesPanel() throws Exception {
        runAndWait(() -> {
            mainWindow.setViewedContactId(UUID.randomUUID());
            logic.setNextResult(new CommandResult("deleted"));
            assertDoesNotThrow(() -> mainWindow.executeCommand("delete 1"));
            assertNull(mainWindow.getViewedContactId());
        });
    }

    @Test
    public void executeCommand_noViewedContact_noRefresh() throws Exception {
        runAndWait(() -> {
            mainWindow.setViewedContactId(null);
            logic.setNextResult(new CommandResult("listed"));
            assertDoesNotThrow(() -> mainWindow.executeCommand("list"));
            assertNull(mainWindow.getViewedContactId());
        });
    }

    /**
     * A stub Logic implementation for testing MainWindow.
     */
    private static class StubLogic implements Logic {
        private CommandResult nextResult = new CommandResult("stub");
        private final ObservableList<Contact> contacts = FXCollections.observableArrayList();

        void setNextResult(CommandResult result) {
            this.nextResult = result;
        }

        ObservableList<Contact> getContacts() {
            return contacts;
        }

        @Override
        public CommandResult execute(String commandText) throws CommandException, ParseException {
            return nextResult;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ObservableList<Contact> getDisplayedContactList() {
            return contacts;
        }

        @Override
        public Path getAddressBookFilePath() {
            return Paths.get("data", "addressbook.json");
        }

        @Override
        public GuiSettings getGuiSettings() {
            return new GuiSettings();
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            // no-op for testing
        }
    }
}
