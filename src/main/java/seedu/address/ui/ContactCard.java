package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.contact.Contact;

/**
 * A UI component that displays information of a {@code Contact}.
 */
public class ContactCard extends UiPart<Region> {

    private static final String FXML = "ContactListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Contact contact;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private VBox notesContainer;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ContactCode} with the given {@code Contact} and index to display.
     */
    public ContactCard(Contact contact, int displayedIndex) {
        super(FXML);
        this.contact = contact;
        id.setText(displayedIndex + ". ");
        name.setText(contact.getName().fullName);
        name.getParent().getParent().setStyle("-fx-background-color: #3c3e3f");
        phone.setText(contact.getPhone().map(phone -> phone.value).orElse(""));
        address.setText(contact.getAddress().map(address -> address.value).orElse(""));
        email.setText(contact.getEmail().map(email -> email.value).orElse(""));
        if (!(contact.getNotes().isEmpty())) {
            contact.getNotes().forEach(
                    note -> {
                        notesContainer.getChildren().add(
                                new NoteLabel(note, notesContainer.getStyleClass().toString())); });
            notesContainer.setStyle("-fx-background-color: #000000");
        } else {
            notesContainer.setVisible(false);
            notesContainer.setManaged(false);
        }
        if (!(contact.getTags().isEmpty() && contact.getReminders().isEmpty())) {
            contact.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
            if (!contact.getReminders().isEmpty()) {
                Label reminderLabel = new Label("Reminder");
                if (contact.hasDueReminders()) {
                    reminderLabel.getStyleClass().add("warning-label");
                }
                tags.getChildren().add(reminderLabel);
            }
        } else {
            tags.setVisible(false);
            tags.setManaged(false);
        }
    }
}
