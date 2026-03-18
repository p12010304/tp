package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Contact;
import seedu.address.testutil.ContactBuilder;

public class MessagesTest {

    @Test
    public void format_allOptionalFieldsPresent_includesAllSegments() {
        Contact contact = new ContactBuilder()
                .withName("Alice Pauline")
                .withPhone("94351253")
                .withEmail("alice@example.com")
                .withAddress("123, Jurong West Ave 6, #08-111")
                .withLastContacted("22/02/26")
                .withTags("friend")
                .build();

        String formatted = Messages.format(contact);
        assertTrue(formatted.startsWith("Alice Pauline"));
        assertTrue(formatted.contains("; Phone: 94351253"));
        assertTrue(formatted.contains("; Email: alice@example.com"));
        assertTrue(formatted.contains("; Address: 123, Jurong West Ave 6, #08-111"));
        assertTrue(formatted.contains("; Last Contacted: 22/02/26"));
        assertTrue(formatted.contains("; Tags: [friend]"));
    }

    @Test
    public void format_missingOptionalFields_omitsAbsentSegments() {
        Contact contact = new ContactBuilder()
                .withName("Bob Choo")
                .withPhone(null)
                .withEmail(null)
                .withAddress(null)
                .withLastContacted(null)
                .build();

        String formatted = Messages.format(contact);
        assertTrue(formatted.startsWith("Bob Choo"));
        assertFalse(formatted.contains("; Phone:"));
        assertFalse(formatted.contains("; Email:"));
        assertFalse(formatted.contains("; Address:"));
        assertFalse(formatted.contains("; Last Contacted:"));
        assertTrue(formatted.contains("; Tags:"));
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_singlePrefix_success() {
        assertEquals(Messages.MESSAGE_DUPLICATE_FIELDS + "p/",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_multiplePrefixes_containsAllPrefixes() {
        String message = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        assertTrue(message.startsWith(Messages.MESSAGE_DUPLICATE_FIELDS));
        assertTrue(message.contains("p/"));
        assertTrue(message.contains("e/"));
        assertTrue(message.contains("a/"));
    }
}
