package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Snapshot;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.ContactBuilder;

/**
 * Tests for undo and redo commands. As redo command depend on the undo command, their tests are placed together.
 */
public class UndoAndRedoCommandTest {
    private static final UndoCommand UNDO_COMMAND = new UndoCommand();
    private static final RedoCommand REDO_COMMAND = new RedoCommand();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_undoCommand_hitLimit() throws Exception {
        assertCommandFailure(UNDO_COMMAND, model, ModelManager.UNDO_LIMIT_MESSAGE);
    }

    @Test
    public void execute_undoCommand_success() throws Exception {
        Contact contact = new ContactBuilder().build();
        Command validCommand = new AddCommand(contact);
        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(contact));
        Snapshot initialSnapshot = model.getSnapshot();
        Model postAdditionModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        postAdditionModel.addContact(contact);

        assertCommandSuccess(validCommand, model, expectedMessage, postAdditionModel);

        Snapshot postCommandSnapshot = model.getSnapshot();
        assertFalse(initialSnapshot.equals(postCommandSnapshot));

        assertCommandSuccess(
                UNDO_COMMAND,
                model,
                String.format(UndoCommand.MESSAGE_UNDO_SUCCESS, expectedMessage),
                new ModelManager(getTypicalAddressBook(), new UserPrefs()));

        Snapshot undoCommandSnapshot = model.getSnapshot();
        assertTrue(initialSnapshot.equals(undoCommandSnapshot));

        assertCommandFailure(new UndoCommand(), model, ModelManager.UNDO_LIMIT_MESSAGE);
    }

    @Test
    public void execute_undoOverwrite_test() throws Exception {
        Contact contact = new ContactBuilder().withName("First Name").build();
        Command validCommand = new AddCommand(contact);
        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(contact));
        Model postAdditionModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        postAdditionModel.addContact(contact);

        assertCommandSuccess(validCommand, model, expectedMessage, postAdditionModel);

        Contact contact2 = new ContactBuilder().withName("Second Name").withPhone("22222222")
                .withEmail("email2@example.com").withAddress("Second address").build();
        Command validCommand2 = new AddCommand(contact2);
        String expectedMessage2 = String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(contact2));
        Model postAdditionModel2 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        postAdditionModel2.addContact(contact);
        postAdditionModel2.addContact(contact2);

        assertCommandSuccess(validCommand2, model, expectedMessage2, postAdditionModel2);

        assertCommandSuccess(
                UNDO_COMMAND,
                model,
                String.format(UndoCommand.MESSAGE_UNDO_SUCCESS, expectedMessage2),
                postAdditionModel);
        assertCommandSuccess(
                UNDO_COMMAND,
                model,
                String.format(UndoCommand.MESSAGE_UNDO_SUCCESS, expectedMessage),
                new ModelManager(getTypicalAddressBook(), new UserPrefs()));

        Model postAdditionModel3 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        postAdditionModel3.addContact(contact2);
        assertCommandSuccess(validCommand2, model, expectedMessage2, postAdditionModel3);
        assertCommandFailure(new RedoCommand(), model, ModelManager.REDO_LIMIT_MESSAGE);
    }

    @Test
    public void execute_redoCommand_hitLimit() throws Exception {
        assertCommandFailure(new RedoCommand(), model, ModelManager.REDO_LIMIT_MESSAGE);
    }

    @Test
    public void execute_redoCommand_success() throws Exception {
        Contact contact = new ContactBuilder().build();
        Command validCommand = new AddCommand(contact);
        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(contact));
        Snapshot initialSnapshot = model.getSnapshot();
        Model postAdditionModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        postAdditionModel.addContact(contact);

        assertCommandSuccess(validCommand, model, expectedMessage, postAdditionModel);

        Snapshot postCommandSnapshot = model.getSnapshot();
        assertFalse(initialSnapshot.equals(postCommandSnapshot));

        assertCommandSuccess(
                UNDO_COMMAND,
                model,
                String.format(UndoCommand.MESSAGE_UNDO_SUCCESS, expectedMessage),
                new ModelManager(getTypicalAddressBook(), new UserPrefs()));
        assertCommandSuccess(
                REDO_COMMAND,
                model,
                String.format(RedoCommand.MESSAGE_REDO_SUCCESS, expectedMessage),
                postAdditionModel);

        Snapshot redoCommandSnapshot = model.getSnapshot();
        assertTrue(postCommandSnapshot.equals(redoCommandSnapshot));

        assertCommandFailure(new RedoCommand(), model, ModelManager.REDO_LIMIT_MESSAGE);
    }
}
