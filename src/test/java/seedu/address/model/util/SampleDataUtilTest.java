package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Contact;


public class SampleDataUtilTest {
    @Test
    public void getSampleContacts() {
        Contact[] contacts = SampleDataUtil.getSampleContacts();
        assertTrue(contacts.length > 0);
    }
}
