package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalContacts.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Contact;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", contactToView=null}";
        assertEquals(expected, commandResult.toString());
    }

    @Test
    public void getContactToView_noContact_returnsEmptyOptional() {
        CommandResult commandResult = new CommandResult("feedback");
        assertTrue(commandResult.getContactToView().isEmpty());
    }

    @Test
    public void getContactToView_withContact_returnsContact() {
        Contact contact = ALICE;
        CommandResult commandResult = new CommandResult("feedback", false, false, contact);
        assertTrue(commandResult.getContactToView().isPresent());
        assertEquals(contact, commandResult.getContactToView().get());
    }

    @Test
    public void isShowContactDetail_noContact_returnsFalse() {
        CommandResult commandResult = new CommandResult("feedback");
        assertFalse(commandResult.isShowContactDetail());
    }

    @Test
    public void isShowContactDetail_withContact_returnsTrue() {
        Contact contact = ALICE;
        CommandResult commandResult = new CommandResult("feedback", false, false, contact);
        assertTrue(commandResult.isShowContactDetail());
    }

    @Test
    public void equals_withContactToView() {
        Contact contact = ALICE;
        CommandResult commandResult = new CommandResult("feedback", false, false, contact);

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, contact)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different contactToView value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false, null)));
        assertFalse(commandResult.equals(new CommandResult("feedback")));
    }

    @Test
    public void hashcode_withContactToView() {
        Contact contact = ALICE;
        CommandResult commandResult = new CommandResult("feedback", false, false, contact);

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(),
                new CommandResult("feedback", false, false, contact).hashCode());

        // different contactToView value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", false, false, null).hashCode());
    }
}
