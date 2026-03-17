package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Contact;
import seedu.address.testutil.ContactBuilder;

/**
 * Contains tests for {@code ContactDetailPanel}.
 */
public class ContactDetailPanelTest extends GuiUnitTest {

    @Test
    public void constructor_success() throws Exception {
        runAndWait(() -> {
            assertDoesNotThrow(() -> new ContactDetailPanel());
        });
    }

    @Test
    public void setContact_validContact_success() throws Exception {
        runAndWait(() -> {
            ContactDetailPanel panel = new ContactDetailPanel();
            assertDoesNotThrow(() -> panel.setContact(ALICE));
            assertDoesNotThrow(() -> panel.setContact(BENSON));
        });
    }

    @Test
    public void setContact_contactWithAllFields_success() throws Exception {
        runAndWait(() -> {
            ContactDetailPanel panel = new ContactDetailPanel();
            Contact fullContact = new ContactBuilder()
                    .withName("Full Contact")
                    .withPhone("12345678")
                    .withEmail("test@example.com")
                    .withAddress("123 Test St")
                    .withLastContacted("22/02/26")
                    .withTags("tag1", "tag2")
                    .withNotes("Test notes")
                    .build();
            assertDoesNotThrow(() -> panel.setContact(fullContact));
        });
    }

    @Test
    public void setContact_contactWithoutOptionalFields_success() throws Exception {
        runAndWait(() -> {
            ContactDetailPanel panel = new ContactDetailPanel();
            Contact minimalContact = new ContactBuilder()
                    .withName("Minimal Contact")
                    .build();
            assertDoesNotThrow(() -> panel.setContact(minimalContact));
        });
    }

    @Test
    public void setContact_contactWithOnlyPhone_success() throws Exception {
        runAndWait(() -> {
            ContactDetailPanel panel = new ContactDetailPanel();
            Contact phoneOnlyContact = new ContactBuilder()
                    .withName("Phone Only")
                    .withPhone("12345678")
                    .build();
            assertDoesNotThrow(() -> panel.setContact(phoneOnlyContact));
        });
    }

    @Test
    public void setContact_contactWithOnlyEmail_success() throws Exception {
        runAndWait(() -> {
            ContactDetailPanel panel = new ContactDetailPanel();
            Contact emailOnlyContact = new ContactBuilder()
                    .withName("Email Only")
                    .withEmail("test@example.com")
                    .build();
            assertDoesNotThrow(() -> panel.setContact(emailOnlyContact));
        });
    }

    @Test
    public void setContact_contactWithOnlyAddress_success() throws Exception {
        runAndWait(() -> {
            ContactDetailPanel panel = new ContactDetailPanel();
            Contact addressOnlyContact = new ContactBuilder()
                    .withName("Address Only")
                    .withAddress("123 Test St")
                    .build();
            assertDoesNotThrow(() -> panel.setContact(addressOnlyContact));
        });
    }

    @Test
    public void setContact_contactWithOnlyLastContacted_success() throws Exception {
        runAndWait(() -> {
            ContactDetailPanel panel = new ContactDetailPanel();
            Contact lastContactedOnlyContact = new ContactBuilder()
                    .withName("Last Contacted Only")
                    .withLastContacted("22/02/26")
                    .build();
            assertDoesNotThrow(() -> panel.setContact(lastContactedOnlyContact));
        });
    }

    @Test
    public void setContact_contactWithOnlyTags_success() throws Exception {
        runAndWait(() -> {
            ContactDetailPanel panel = new ContactDetailPanel();
            Contact tagsOnlyContact = new ContactBuilder()
                    .withName("Tags Only")
                    .withTags("tag1", "tag2", "tag3")
                    .build();
            assertDoesNotThrow(() -> panel.setContact(tagsOnlyContact));
        });
    }

    @Test
    public void setContact_contactWithOnlyNotes_success() throws Exception {
        runAndWait(() -> {
            ContactDetailPanel panel = new ContactDetailPanel();
            Contact notesOnlyContact = new ContactBuilder()
                    .withName("Notes Only")
                    .withNotes("Some notes here")
                    .build();
            assertDoesNotThrow(() -> panel.setContact(notesOnlyContact));
        });
    }

    @Test
    public void setContact_contactWithEmptyNotes_success() throws Exception {
        runAndWait(() -> {
            ContactDetailPanel panel = new ContactDetailPanel();
            Contact emptyNotesContact = new ContactBuilder()
                    .withName("Empty Notes")
                    .withNotes("")
                    .build();
            assertDoesNotThrow(() -> panel.setContact(emptyNotesContact));
        });
    }

    @Test
    public void setContact_null_clearsPanel() throws Exception {
        runAndWait(() -> {
            ContactDetailPanel panel = new ContactDetailPanel();
            panel.setContact(ALICE);
            assertDoesNotThrow(() -> panel.setContact(null));
        });
    }

    @Test
    public void clearContact_success() throws Exception {
        runAndWait(() -> {
            ContactDetailPanel panel = new ContactDetailPanel();
            panel.setContact(ALICE);
            assertDoesNotThrow(() -> panel.clearContact());
        });
    }

    @Test
    public void setContact_multipleContacts_success() throws Exception {
        runAndWait(() -> {
            ContactDetailPanel panel = new ContactDetailPanel();
            assertDoesNotThrow(() -> {
                panel.setContact(ALICE);
                panel.setContact(BENSON);
                panel.clearContact();
                panel.setContact(ALICE);
            });
        });
    }

    @Test
    public void setContact_alternatingFullAndMinimal_success() throws Exception {
        runAndWait(() -> {
            ContactDetailPanel panel = new ContactDetailPanel();
            Contact fullContact = new ContactBuilder()
                    .withName("Full")
                    .withPhone("12345678")
                    .withEmail("full@test.com")
                    .withAddress("Full Address")
                    .withTags("tag1")
                    .withNotes("Full notes")
                    .build();
            Contact minimalContact = new ContactBuilder()
                    .withName("Minimal")
                    .build();
            assertDoesNotThrow(() -> {
                panel.setContact(fullContact);
                panel.setContact(minimalContact);
                panel.setContact(fullContact);
            });
        });
    }
}
