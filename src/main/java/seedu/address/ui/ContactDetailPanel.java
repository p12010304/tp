package seedu.address.ui;

import java.util.Comparator;

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
    private VBox notesContainer;

    /**
     * Creates an empty {@code ContactDetailPanel}.
     */
    public ContactDetailPanel() {
        super(FXML);
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
            phoneContainer.setVisible(true);
            phoneContainer.setManaged(true);
        } else {
            phoneContainer.setVisible(false);
            phoneContainer.setManaged(false);
        }

        // Email
        if (contact.getEmail().isPresent()) {
            email.setText(contact.getEmail().get().value);
            emailContainer.setVisible(true);
            emailContainer.setManaged(true);
        } else {
            emailContainer.setVisible(false);
            emailContainer.setManaged(false);
        }

        // Address
        if (contact.getAddress().isPresent()) {
            address.setText(contact.getAddress().get().value);
            addressContainer.setVisible(true);
            addressContainer.setManaged(true);
        } else {
            addressContainer.setVisible(false);
            addressContainer.setManaged(false);
        }

        // Notes
        notes.getChildren().clear();
        if (!contact.getNotes().isEmpty()) {
            contact.getNotes().forEach(note -> {
                NoteLabel reminderLabel = new NoteLabel(note, notes.getStyleClass().toString());
                reminderLabel.hideHeader();
                notes.getChildren().add(reminderLabel); });
            notesContainer.setVisible(true);
            notesContainer.setManaged(true);
        } else {
            notesContainer.setVisible(false);
            notesContainer.setManaged(false);
        }

        // Tags
        tags.getChildren().clear();
        if (!contact.getTags().isEmpty()) {
            contact.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
            tagsContainer.setVisible(true);
            tagsContainer.setManaged(true);
        } else {
            tagsContainer.setVisible(false);
            tagsContainer.setManaged(false);
        }

        detailPane.setVisible(true);
        detailPane.setManaged(true);
    }

    /**
     * Clears the contact details panel.
     */
    public void clearContact() {
        name.setText("");
        phone.setText("");
        email.setText("");
        address.setText("");
        tags.getChildren().clear();
        notes.getChildren().clear();

        phoneContainer.setVisible(false);
        phoneContainer.setManaged(false);
        emailContainer.setVisible(false);
        emailContainer.setManaged(false);
        addressContainer.setVisible(false);
        addressContainer.setManaged(false);
        notesContainer.setVisible(false);
        notesContainer.setManaged(false);
        tagsContainer.setVisible(false);
        tagsContainer.setManaged(false);

        detailPane.setVisible(false);
        detailPane.setManaged(false);
    }
}
