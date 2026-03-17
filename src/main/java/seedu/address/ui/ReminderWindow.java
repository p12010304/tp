package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Note;

/**
 * Controller for a help page
 */
public class ReminderWindow extends UiPart<Stage> {

    public static final String REMINDER_MESSAGE =
            String.format("The following reminders are due in %d days: \n", Note.DUE_PERIOD_DAYS);

    private static final Logger logger = LogsCenter.getLogger(ReminderWindow.class);
    private static final String FXML = "ReminderWindow.fxml";

    @FXML
    private Label reminderMessage;

    @FXML
    private VBox reminderMessageContainer;

    /**
     * Creates a new ReminderWindow.
     *
     * @param root Stage to use as the root of the ReminderWindow.
     */
    public ReminderWindow(Stage root, List<Contact> contactList) {
        super(FXML, root);
        reminderMessage.setText(REMINDER_MESSAGE);
        contactList.stream().filter(Contact::hasDueReminders).forEach(contact -> {
            Label nameLabel = new Label(contact.getName().toString() + ":   ");
            nameLabel.getStyleClass().add("name");
            reminderMessageContainer.getChildren().add(nameLabel);
            contact.getNotes().stream()
                    .filter(Note :: hasDueReminder)
                    .forEach(reminder -> {
                        NoteLabel reminderLabel = new NoteLabel(reminder);
                        reminderLabel.hideHeader();
                        reminderMessageContainer.getChildren().add(reminderLabel); });
        });
    }

    /**
     * Creates a new HelpWindow.
     */
    public ReminderWindow(List<Contact> contactList) {
        this(new Stage(), contactList);
    }

    /**
     * Shows the reminder window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
