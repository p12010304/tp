package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Note;

/**
 * A UI component that displays information of a {@code Note}.
 * Contact references ({@code @{UUID}}) are rendered as bold, underlined contact names.
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
     * Creates a {@code NoteLabel} with contact reference resolution.
     */
    public NoteLabel(Note note, ObservableList<Contact> contacts) {
        requireNonNull(note);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Apply cell_small_label font to match phone/email/address style
        reminderNote.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 13px;");

        if (note.hasContactReferences() && contacts != null) {
            // Replace the plain label with a TextFlow containing styled contact names
            int idx = getChildren().indexOf(reminderNote);
            TextFlow textFlow = buildRichNoteText(note.value, contacts);
            getChildren().set(idx, textFlow);
        } else {
            reminderNote.setText(note.value);
        }

        if (note.timePoint != null) {
            reminderTime.setText(note.timePoint.toString());
        } else {
            NodeUtil.hide(reminderHeader);
            NodeUtil.hide(onText);
            NodeUtil.hide(reminderTime);
        }
    }

    /**
     * Creates a {@code NoteLabel} without contact reference resolution.
     */
    public NoteLabel(Note note) {
        this(note, (ObservableList<Contact>) null);
    }

    /**
     * Creates a {@code NoteLabel} with a set style and contact reference resolution.
     */
    public NoteLabel(Note note, String style, ObservableList<Contact> contacts) {
        this(note, contacts);
        reminderHeader.getStyleClass().add(style);
        onText.getStyleClass().add(style);
        reminderTime.getStyleClass().add(style);
    }

    /**
     * Creates a {@code NoteLabel} with a set style.
     */
    public NoteLabel(Note note, String style) {
        this(note, style, null);
    }

    /**
     * Builds a {@code TextFlow} from note text, resolving {@code @{UUID}} references
     * to bold/underlined contact names.
     */
    private TextFlow buildRichNoteText(String noteText, ObservableList<Contact> contacts) {
        TextFlow textFlow = new TextFlow();
        Matcher matcher = Note.CONTACT_REF_PATTERN.matcher(noteText);
        int lastEnd = 0;

        while (matcher.find()) {
            // Add plain text before this match
            if (matcher.start() > lastEnd) {
                Text plainText = new Text(noteText.substring(lastEnd, matcher.start()));
                plainText.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-fill: white;");
                textFlow.getChildren().add(plainText);
            }

            // Resolve UUID to contact name
            String uuidStr = matcher.group(1);
            UUID refId = UUID.fromString(uuidStr);
            String displayName = contacts.stream()
                    .filter(c -> c.getId().equals(refId))
                    .map(c -> c.getName().fullName)
                    .findFirst()
                    .orElse("@" + uuidStr);

            Text refText = new Text(displayName);
            refText.getStyleClass().add("contact-reference");
            refText.setStyle("-fx-font-size: 13px;");
            textFlow.getChildren().add(refText);

            lastEnd = matcher.end();
        }

        // Add remaining plain text
        if (lastEnd < noteText.length()) {
            Text plainText = new Text(noteText.substring(lastEnd));
            plainText.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 13px; -fx-fill: white;");
            textFlow.getChildren().add(plainText);
        }

        return textFlow;
    }

    /**
     * Hides the "Reminder: " header of the {@code ReminderLabel}.
     */
    public void hideHeader() {
        NodeUtil.hide(reminderHeader);
    }
}
