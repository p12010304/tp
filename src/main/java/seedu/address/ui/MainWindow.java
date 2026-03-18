package seedu.address.ui;

import java.util.UUID;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Contact;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ContactListPanel contactListPanel;
    private ContactDetailPanel contactDetailPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ReminderWindow reminderWindow;

    /** The UUID of the contact currently shown in the detail panel, or null if none. */
    private UUID viewedContactId;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane contactListPanelPlaceholder;

    @FXML
    private StackPane contactDetailPanelPlaceholder;

    @FXML
    private VBox contactDetailContainer;

    @FXML
    private SplitPane splitPane;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        contactListPanel = new ContactListPanel(logic.getDisplayedContactList());
        contactListPanelPlaceholder.getChildren().add(contactListPanel.getRoot());

        contactDetailPanel = new ContactDetailPanel();
        contactDetailPanelPlaceholder.getChildren().add(contactDetailPanel.getRoot());

        // Initially hide the detail panel
        hideContactDetailPanel();

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        if (logic.getDisplayedContactList().stream().anyMatch(Contact::hasDueReminders)) {
            reminderWindow = new ReminderWindow(logic.getDisplayedContactList());
            reminderWindow.show();
        }
    }

    /**
     * Shows the contact detail panel.
     */
    private void showContactDetailPanel() {
        contactDetailContainer.setVisible(true);
        contactDetailContainer.setManaged(true);
        splitPane.setDividerPositions(0.6);
    }

    /**
     * Hides the contact detail panel.
     */
    private void hideContactDetailPanel() {
        contactDetailContainer.setVisible(false);
        contactDetailContainer.setManaged(false);
        splitPane.setDividerPositions(1.0);
        viewedContactId = null;
    }

    /**
     * Refreshes the contact detail panel with the latest data for the currently viewed contact.
     * If the contact is no longer in the filtered list (e.g. deleted or filtered out),
     * the detail panel is hidden.
     */
    private void refreshContactDetailPanel() {
        Contact updatedContact = logic.getDisplayedContactList().stream()
                .filter(c -> c.getId().equals(viewedContactId))
                .findFirst()
                .orElse(null);

        if (updatedContact != null) {
            contactDetailPanel.setContact(updatedContact);
        } else {
            contactDetailPanel.clearContact();
            hideContactDetailPanel();
        }
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public ContactListPanel getContactListPanel() {
        return contactListPanel;
    }

    /**
     * Returns the UUID of the currently viewed contact, for testing.
     */
    UUID getViewedContactId() {
        return viewedContactId;
    }

    /**
     * Sets the UUID of the currently viewed contact, for testing.
     */
    void setViewedContactId(UUID id) {
        this.viewedContactId = id;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isHideContactDetail()) {
                contactDetailPanel.clearContact();
                hideContactDetailPanel();
            } else if (commandResult.isShowContactDetail()) {
                commandResult.getContactToView().ifPresent(contact -> {
                    contactDetailPanel.setContact(contact);
                    viewedContactId = contact.getId();
                    showContactDetailPanel();
                });
            } else if (viewedContactId != null) {
                refreshContactDetailPanel();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
