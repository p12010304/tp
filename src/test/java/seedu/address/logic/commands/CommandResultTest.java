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
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, null, false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false, null, false, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true, null, false, false)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", true, false, null, false, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", false, true, null, false, false).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", contactToView=null"
                + ", hideViewPanel=false}";
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
        CommandResult commandResult = new CommandResult("feedback", false, false, contact, false, false);
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
        CommandResult commandResult = new CommandResult("feedback", false, false, contact, false, false);
        assertTrue(commandResult.isShowContactDetail());
    }

    @Test
    public void equals_withContactToView() {
        Contact contact = ALICE;
        CommandResult commandResult = new CommandResult("feedback", false, false, contact, false, false);

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, contact, false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different contactToView value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false, null, false, false)));
        assertFalse(commandResult.equals(new CommandResult("feedback")));
    }

    @Test
    public void hashcode_withContactToView() {
        Contact contact = ALICE;
        CommandResult commandResult = new CommandResult("feedback", false, false, contact, false, false);

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(),
                new CommandResult("feedback", false, false, contact, false, false).hashCode());

        // different contactToView value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", false, false, null, false, false).hashCode());
    }

    @Test
    public void isHideContactDetail_defaultFalse() {
        CommandResult commandResult = new CommandResult("feedback");
        assertFalse(commandResult.isHideViewPanel());
    }

    @Test
    public void isHideContactDetail_setTrue() {
        CommandResult commandResult = new CommandResult("feedback", false, false, null, true, false);
        assertTrue(commandResult.isHideViewPanel());
    }

    @Test
    public void equals_withHideContactDetail() {
        CommandResult commandResult = new CommandResult("feedback", false, false, null, true, false);

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, null, true, false)));

        // different hideContactDetail -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false, null, false, false)));
    }

    @Test
    public void hashcode_withHideContactDetail() {
        CommandResult commandResult = new CommandResult("feedback", false, false, null, true, false);

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(),
                new CommandResult("feedback", false, false, null, true, false).hashCode());

        // different hideContactDetail -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult("feedback", false, false, null, false, false).hashCode());
    }
}
