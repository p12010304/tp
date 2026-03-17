package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.contact.Note;

/**
 * A UI component that displays information of a {@code Reminder}.
 */
public class NoteLabel extends HBox {

    private static final String FXML = "/view/ReminderLabel.fxml";

    @FXML
    private Label reminderHeader;
    @FXML
    private Label reminderNote;
    @FXML
    private Label onText;
    @FXML
    private Label reminderTime;

    /**
     * Creates a {@code ReminderLabel}.
     */
    public NoteLabel(Note note) {
        requireNonNull(note);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        reminderNote.setText(note.value);

        if (note.timePoint != null) {
            reminderTime.setText(note.timePoint.toString());
        } else {
            reminderHeader.setVisible(false);
            reminderHeader.setManaged(false);
            onText.setVisible(false);
            onText.setManaged(false);
            reminderTime.setVisible(false);
            reminderTime.setManaged(false);
        }
    }

    /**
     * Creates a {@code ReminderLabel} with a set style.
     */
    public NoteLabel(Note note, String style) {
        this(note);
        reminderHeader.getStyleClass().add(style);
        reminderNote.getStyleClass().add(style);
        onText.getStyleClass().add(style);
        reminderTime.getStyleClass().add(style);
    }

    /**
     * Hides the "Reminder: " header of the {@code ReminderLabel}.
     */
    public void hideHeader() {
        reminderHeader.setVisible(false);
        reminderHeader.setManaged(false);
    }
}
