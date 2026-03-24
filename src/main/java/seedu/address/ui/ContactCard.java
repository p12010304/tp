package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.contact.Contact;
import seedu.address.model.tag.RankedTag;

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
    private Label lastContacted;
    @FXML
    private Label lastUpdated;
    @FXML
    private VBox notesContainer;
    @FXML
    private FlowPane tags;

    private final ObservableList<Contact> allContacts;

    /**
     * Creates a {@code ContactCode} with the given {@code Contact} and index to display.
     */
    public ContactCard(Contact contact, int displayedIndex, ObservableList<Contact> allContacts) {
        super(FXML);
        this.contact = contact;
        this.allContacts = allContacts;
        id.setText(displayedIndex + ". ");
        name.setText(contact.getName().fullName);
        name.getParent().getParent().setStyle("-fx-background-color: #3c3e3f");
        contact.getPhone().ifPresentOrElse(phone -> {
            this.phone.setText(phone.value);
            NodeUtil.show(this.phone);
        }, () -> {
            this.phone.setText("");
            NodeUtil.hide(this.phone);
        });
        contact.getAddress().ifPresentOrElse(address -> {
            this.address.setText(address.value);
            NodeUtil.show(this.address);
        }, () -> {
            this.address.setText("");
            NodeUtil.hide(this.address);
        });
        contact.getEmail().ifPresentOrElse(email -> {
            this.email.setText(email.value);
            NodeUtil.show(this.email);
        }, () -> {
            this.email.setText("");
            NodeUtil.hide(this.email);
        });
        contact.getLastContacted().ifPresentOrElse(lastContacted -> {
            this.lastContacted.setText("Last Contacted: " + lastContacted);
            NodeUtil.show(this.lastContacted);
        }, () -> {
            this.lastContacted.setText("");
            NodeUtil.hide(this.lastContacted);
        });
        this.lastUpdated.setText("Last Updated: " + contact.getLastUpdated());
        NodeUtil.show(this.lastUpdated);
        if (!(contact.getNotes().isEmpty())) {
            contact.getNotes().forEach(
                    note -> {
                        notesContainer.getChildren().add(
                                new NoteLabel(note, notesContainer.getStyleClass().toString(),
                                allContacts)); });
            notesContainer.setStyle("-fx-background-color: #000000");
        } else {
            NodeUtil.hide(notesContainer);
        }
        if (!(contact.getTags().isEmpty() && contact.getReminders().isEmpty())) {
            contact.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.name))
                    .forEach(tag -> tags.getChildren().add(
                        tag instanceof RankedTag
                            ? new RankedTagLabel((RankedTag) tag)
                            : new Label(tag.name)));

            if (!contact.getReminders().isEmpty()) {
                Label reminderLabel = new Label("Reminder");
                if (contact.hasDueReminders()) {
                    reminderLabel.getStyleClass().add("warning-label");
                }
                tags.getChildren().add(reminderLabel);
            }
        } else {
            NodeUtil.hide(tags);
        }
    }
}
