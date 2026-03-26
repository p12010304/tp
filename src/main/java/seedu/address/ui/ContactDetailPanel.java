package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.contact.Contact;

/**
 * A UI component that displays detailed information of a {@code Contact}.
 */
public class ContactDetailPanel extends UiPart<Region> {

    private static final String FXML = "ContactDetailPanel.fxml";

    @FXML
    private VBox detailPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label address;
    @FXML
    private Label lastContacted;
    @FXML
    private Label lastUpdated;
    @FXML
    private VBox notes;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox tagsContainer;
    @FXML
    private VBox phoneContainer;
    @FXML
    private VBox emailContainer;
    @FXML
    private VBox addressContainer;
    @FXML
    private VBox lastContactedContainer;
    @FXML
    private VBox lastUpdatedContainer;
    @FXML
    private VBox notesContainer;

    private ObservableList<Contact> allContacts;

    /**
     * Creates an empty {@code ContactDetailPanel}.
     */
    public ContactDetailPanel(ObservableList<Contact> allContacts) {
        super(FXML);
        this.allContacts = allContacts;
        clearContact();
    }

    /**
     * Updates the panel to display the given contact's details.
     */
    public void setContact(Contact contact) {
        if (contact == null) {
            clearContact();
            return;
        }

        name.setText(contact.getName().fullName);

        // Phone
        if (contact.getPhone().isPresent()) {
            phone.setText(contact.getPhone().get().value);
            NodeUtil.show(phoneContainer);
        } else {
            NodeUtil.hide(phoneContainer);
        }

        // Email
        if (contact.getEmail().isPresent()) {
            email.setText(contact.getEmail().get().value);
            NodeUtil.show(emailContainer);
        } else {
            NodeUtil.hide(emailContainer);
        }

        // Address
        if (contact.getAddress().isPresent()) {
            address.setText(contact.getAddress().get().value);
            NodeUtil.show(addressContainer);
        } else {
            NodeUtil.hide(addressContainer);
        }

        // Last Contacted
        if (contact.getLastContacted().isPresent()) {
            lastContacted.setText(contact.getLastContacted().get().toString());
            NodeUtil.show(lastContactedContainer);
        } else {
            NodeUtil.hide(lastContactedContainer);
        }

        // Last Updated
        lastUpdated.setText(contact.getLastUpdated().toString());
        NodeUtil.show(lastUpdatedContainer);

        // Notes
        notes.getChildren().clear();
        if (!contact.getNotes().isEmpty()) {
            contact.getNotes().forEach(note -> {
                NoteLabel noteLabel = new NoteLabel(note,
                        notes.getStyleClass().toString(), allContacts);
                noteLabel.hideHeader();
                notes.getChildren().add(noteLabel); });
            NodeUtil.show(notesContainer);
        } else {
            NodeUtil.hide(notesContainer);
        }

        // Tags
        tags.getChildren().clear();
        if (!contact.getTags().isEmpty()) {
            contact.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.name))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.name)));
            NodeUtil.show(tagsContainer);
        } else {
            NodeUtil.hide(tagsContainer);
        }

        NodeUtil.show(detailPane);
    }

    /**
     * Clears the contact details panel.
     */
    public void clearContact() {
        name.setText("");
        phone.setText("");
        email.setText("");
        address.setText("");
        lastContacted.setText("");
        lastUpdated.setText("");
        tags.getChildren().clear();
        notes.getChildren().clear();

        NodeUtil.hide(phoneContainer);
        NodeUtil.hide(emailContainer);
        NodeUtil.hide(addressContainer);
        NodeUtil.hide(lastContactedContainer);
        NodeUtil.hide(lastUpdatedContainer);
        NodeUtil.hide(notesContainer);
        NodeUtil.hide(tagsContainer);

        NodeUtil.hide(detailPane);
    }
}
