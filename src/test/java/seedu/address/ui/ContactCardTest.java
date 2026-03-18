package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.contact.Note;
import seedu.address.model.timepoint.TimePoint;
import seedu.address.testutil.ContactBuilder;

/**
 * Contains tests for {@code ContactCard}.
 */
public class ContactCardTest extends GuiUnitTest {

    @Test
    public void constructor_allOptionalsPresent_success() throws Exception {
        runAndWait(() -> {
            ContactCard card = new ContactCard(new ContactBuilder()
                    .withName("Alice Pauline")
                    .withPhone("94351253")
                    .withEmail("alice@example.com")
                    .withAddress("123, Jurong West Ave 6, #08-111")
                    .withLastContacted("22/02/26")
                    .withTags("friends", "teammate")
                    .withNotes("follow up")
                    .build(), 1);

            Label phone = getPrivateField(card, "phone", Label.class);
            Label email = getPrivateField(card, "email", Label.class);
            Label address = getPrivateField(card, "address", Label.class);
            Label lastContacted = getPrivateField(card, "lastContacted", Label.class);
            VBox notesContainer = getPrivateField(card, "notesContainer", VBox.class);
            FlowPane tags = getPrivateField(card, "tags", FlowPane.class);

            assertEquals("94351253", phone.getText());
            assertTrue(phone.isVisible() && phone.isManaged());
            assertEquals("alice@example.com", email.getText());
            assertTrue(email.isVisible() && email.isManaged());
            assertEquals("123, Jurong West Ave 6, #08-111", address.getText());
            assertTrue(address.isVisible() && address.isManaged());
            assertEquals("Last Contacted: 22/02/26", lastContacted.getText());
            assertTrue(lastContacted.isVisible() && lastContacted.isManaged());
            assertTrue(notesContainer.isVisible() && notesContainer.isManaged());
            assertFalse(notesContainer.getChildren().isEmpty());
            assertTrue(tags.isVisible() && tags.isManaged());
            assertFalse(tags.getChildren().isEmpty());
        });
    }

    @Test
    public void constructor_optionalsAbsent_hidesLabelsAndContainers() throws Exception {
        runAndWait(() -> {
            ContactCard card = new ContactCard(new ContactBuilder()
                    .withName("Minimal Contact")
                    .withPhone(null)
                    .withEmail(null)
                    .withAddress(null)
                    .withLastContacted(null)
                    .build(), 2);

            Label phone = getPrivateField(card, "phone", Label.class);
            Label email = getPrivateField(card, "email", Label.class);
            Label address = getPrivateField(card, "address", Label.class);
            Label lastContacted = getPrivateField(card, "lastContacted", Label.class);
            VBox notesContainer = getPrivateField(card, "notesContainer", VBox.class);
            FlowPane tags = getPrivateField(card, "tags", FlowPane.class);

            assertEquals("", phone.getText());
            assertFalse(phone.isVisible() || phone.isManaged());
            assertEquals("", email.getText());
            assertFalse(email.isVisible() || email.isManaged());
            assertEquals("", address.getText());
            assertFalse(address.isVisible() || address.isManaged());
            assertEquals("", lastContacted.getText());
            assertFalse(lastContacted.isVisible() || lastContacted.isManaged());
            assertFalse(notesContainer.isVisible() || notesContainer.isManaged());
            assertFalse(tags.isVisible() || tags.isManaged());
        });
    }

    @Test
    public void constructor_dueReminder_addsReminderLabelWithWarningStyle() throws Exception {
        runAndWait(() -> {
            TimePoint dueReminderTime = TimePoint.of(LocalDate.now().plusDays(Note.DUE_PERIOD_DAYS - 1));
            String dueReminderString = "follow up " + CliSyntax.PREFIX_ON + dueReminderTime;

            ContactCard card = new ContactCard(new ContactBuilder()
                    .withName("Reminder Contact")
                    .withPhone(null)
                    .withEmail(null)
                    .withAddress(null)
                    .withNotes(dueReminderString)
                    .build(), 3);

            FlowPane tags = getPrivateField(card, "tags", FlowPane.class);
            List<Label> labels = tags.getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .map(node -> (Label) node)
                    .collect(Collectors.toList());

            Label reminderLabel = labels.stream()
                    .filter(label -> "Reminder".equals(label.getText()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Reminder label not found"));

            assertTrue(reminderLabel.getStyleClass().contains("warning-label"));
        });
    }

    private static <T> T getPrivateField(ContactCard card, String fieldName, Class<T> fieldType) {
        try {
            Field field = ContactCard.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return fieldType.cast(field.get(card));
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Unable to read field: " + fieldName, e);
        }
    }
}
