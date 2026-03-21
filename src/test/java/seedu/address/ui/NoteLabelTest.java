package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Note;
import seedu.address.model.timepoint.TimePoint;
import seedu.address.testutil.ContactBuilder;

/**
 * Contains tests for {@code NoteLabel}.
 */
public class NoteLabelTest extends GuiUnitTest {

    @Test
    public void constructor_plainNote_success() throws Exception {
        runAndWait(() -> {
            Note note = new Note("plain note text");
            assertDoesNotThrow(() -> new NoteLabel(note));
        });
    }

    @Test
    public void constructor_noteWithStyle_success() throws Exception {
        runAndWait(() -> {
            Note note = new Note("styled note");
            assertDoesNotThrow(() -> new NoteLabel(note, "detail-value"));
        });
    }

    @Test
    public void constructor_noteWithReminder_success() throws Exception {
        runAndWait(() -> {
            Note note = new Note("reminder note", TimePoint.of("2026-12-25"));
            assertDoesNotThrow(() -> new NoteLabel(note));
        });
    }

    @Test
    public void constructor_noteWithReminderAndStyle_success() throws Exception {
        runAndWait(() -> {
            Note note = new Note("reminder note", TimePoint.of("2026-12-25"));
            assertDoesNotThrow(() -> new NoteLabel(note, "detail-value"));
        });
    }

    @Test
    public void constructor_noteWithContactRef_resolvesName() throws Exception {
        runAndWait(() -> {
            Contact alice = new ContactBuilder().withName("Alice Pauline").build();
            UUID aliceId = alice.getId();
            Note note = new Note("worked with @{" + aliceId + "}");
            ObservableList<Contact> contacts = FXCollections.observableArrayList(alice);

            NoteLabel label = new NoteLabel(note, contacts);
            assertNotNull(label);

            // Should contain a TextFlow instead of a plain label
            boolean hasTextFlow = label.getChildren().stream()
                    .anyMatch(node -> node instanceof TextFlow);
            assertTrue(hasTextFlow);

            // The TextFlow should contain the contact name
            TextFlow textFlow = (TextFlow) label.getChildren().stream()
                    .filter(node -> node instanceof TextFlow)
                    .findFirst().get();
            boolean hasAliceName = textFlow.getChildren().stream()
                    .filter(node -> node instanceof Text)
                    .map(node -> ((Text) node).getText())
                    .anyMatch(text -> text.contains("Alice Pauline"));
            assertTrue(hasAliceName);
        });
    }

    @Test
    public void constructor_noteWithContactRefAndStyle_success() throws Exception {
        runAndWait(() -> {
            Contact alice = new ContactBuilder().withName("Alice Pauline").build();
            UUID aliceId = alice.getId();
            Note note = new Note("worked with @{" + aliceId + "}");
            ObservableList<Contact> contacts = FXCollections.observableArrayList(alice);

            assertDoesNotThrow(() -> new NoteLabel(note, "detail-value", contacts));
        });
    }

    @Test
    public void constructor_noteWithUnknownUuid_showsFallback() throws Exception {
        runAndWait(() -> {
            UUID unknownId = UUID.randomUUID();
            Note note = new Note("worked with @{" + unknownId + "}");
            ObservableList<Contact> contacts = FXCollections.observableArrayList();

            NoteLabel label = new NoteLabel(note, contacts);
            assertNotNull(label);

            // Should still contain a TextFlow with fallback text
            TextFlow textFlow = (TextFlow) label.getChildren().stream()
                    .filter(node -> node instanceof TextFlow)
                    .findFirst().get();
            boolean hasFallback = textFlow.getChildren().stream()
                    .filter(node -> node instanceof Text)
                    .map(node -> ((Text) node).getText())
                    .anyMatch(text -> text.startsWith("@"));
            assertTrue(hasFallback);
        });
    }

    @Test
    public void constructor_noteWithMixedTextAndRef_showsBoth() throws Exception {
        runAndWait(() -> {
            Contact bob = new ContactBuilder().withName("Bob Builder").build();
            UUID bobId = bob.getId();
            Note note = new Note("met @{" + bobId + "} at conference");
            ObservableList<Contact> contacts = FXCollections.observableArrayList(bob);

            NoteLabel label = new NoteLabel(note, contacts);
            TextFlow textFlow = (TextFlow) label.getChildren().stream()
                    .filter(node -> node instanceof TextFlow)
                    .findFirst().get();

            // Should have 3 children: "met ", "Bob Builder", " at conference"
            assertEquals(3, textFlow.getChildren().size());

            Text before = (Text) textFlow.getChildren().get(0);
            assertEquals("met ", before.getText());

            Text refText = (Text) textFlow.getChildren().get(1);
            assertEquals("Bob Builder", refText.getText());
            assertTrue(refText.getStyleClass().contains("contact-reference"));

            Text after = (Text) textFlow.getChildren().get(2);
            assertEquals(" at conference", after.getText());
        });
    }

    @Test
    public void constructor_noteWithRefButNullContacts_showsPlainText() throws Exception {
        runAndWait(() -> {
            UUID someId = UUID.randomUUID();
            Note note = new Note("worked with @{" + someId + "}");

            // null contacts should not create TextFlow
            NoteLabel label = new NoteLabel(note, (ObservableList<Contact>) null);
            boolean hasTextFlow = label.getChildren().stream()
                    .anyMatch(node -> node instanceof TextFlow);
            assertFalse(hasTextFlow);
        });
    }

    @Test
    public void constructor_noteWithMultipleRefs_resolvesAll() throws Exception {
        runAndWait(() -> {
            Contact alice = new ContactBuilder().withName("Alice").build();
            Contact bob = new ContactBuilder().withName("Bob").build();
            UUID aliceId = alice.getId();
            UUID bobId = bob.getId();
            Note note = new Note("@{" + aliceId + "} and @{" + bobId + "}");
            ObservableList<Contact> contacts = FXCollections.observableArrayList(alice, bob);

            NoteLabel label = new NoteLabel(note, contacts);
            TextFlow textFlow = (TextFlow) label.getChildren().stream()
                    .filter(node -> node instanceof TextFlow)
                    .findFirst().get();

            // Should have 3 children: "Alice", " and ", "Bob"
            assertEquals(3, textFlow.getChildren().size());
        });
    }

    @Test
    public void hideHeader_hidesReminderHeader() throws Exception {
        runAndWait(() -> {
            Note note = new Note("test note", TimePoint.of("2026-12-25"));
            NoteLabel label = new NoteLabel(note);
            assertDoesNotThrow(() -> label.hideHeader());
        });
    }

    @Test
    public void constructor_noteStartingWithRef_success() throws Exception {
        runAndWait(() -> {
            Contact alice = new ContactBuilder().withName("Alice").build();
            UUID aliceId = alice.getId();
            Note note = new Note("@{" + aliceId + "} is great");
            ObservableList<Contact> contacts = FXCollections.observableArrayList(alice);

            NoteLabel label = new NoteLabel(note, contacts);
            TextFlow textFlow = (TextFlow) label.getChildren().stream()
                    .filter(node -> node instanceof TextFlow)
                    .findFirst().get();

            // Should have 2 children: "Alice", " is great"
            assertEquals(2, textFlow.getChildren().size());
            assertEquals("Alice", ((Text) textFlow.getChildren().get(0)).getText());
        });
    }

    @Test
    public void constructor_noteEndingWithRef_success() throws Exception {
        runAndWait(() -> {
            Contact alice = new ContactBuilder().withName("Alice").build();
            UUID aliceId = alice.getId();
            Note note = new Note("works with @{" + aliceId + "}");
            ObservableList<Contact> contacts = FXCollections.observableArrayList(alice);

            NoteLabel label = new NoteLabel(note, contacts);
            TextFlow textFlow = (TextFlow) label.getChildren().stream()
                    .filter(node -> node instanceof TextFlow)
                    .findFirst().get();

            // Should have 2 children: "works with ", "Alice"
            assertEquals(2, textFlow.getChildren().size());
            assertEquals("Alice", ((Text) textFlow.getChildren().get(1)).getText());
        });
    }
}
