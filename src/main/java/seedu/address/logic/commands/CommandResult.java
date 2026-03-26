package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contact.Contact;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** Contact to view in detail panel. */
    private final Contact contactToView;

    /** Whether to hide the view panel. */
    private final boolean hideViewPanel;

    /** Whether to hide the view panel. */
    private final boolean showFileList;

    /**
     * Constructs a {@code CommandResult} with all fields specified.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
            Contact contactToView, boolean hideViewPanel, boolean showFileList) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.contactToView = contactToView;
        this.hideViewPanel = hideViewPanel;
        this.showFileList = showFileList;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, null, false, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    /**
     * Returns the contact to view, if any.
     */
    public Optional<Contact> getContactToView() {
        return Optional.ofNullable(contactToView);
    }

    /**
     * Returns true if widget should show contact details.
     */
    public boolean isShowContactDetail() {
        return contactToView != null;
    }

    /**
     * Returns true if the contact detail panel should be hidden.
     */
    public boolean isHideViewPanel() {
        return hideViewPanel;
    }

    /**
     * Returns true if the file list should be shown.
     */
    public boolean isShowFileList() {
        return showFileList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && Objects.equals(contactToView, otherCommandResult.contactToView)
                && hideViewPanel == otherCommandResult.hideViewPanel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, contactToView, hideViewPanel);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("contactToView", contactToView)
                .add("hideViewPanel", hideViewPanel)
                .toString();
    }
}

class HelpCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * {@code showHelp} set to {@code true},
     * and other fields set to their default value.
     */
    public HelpCommandResult(String feedbackToUser) {
        super(feedbackToUser, true, false, null, false, false);
    }
}

class ExitCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * {@code exit} set to {@code true},
     * and other fields set to their default value.
     */
    public ExitCommandResult(String feedbackToUser) {
        super(feedbackToUser, false, true, null, false, false);
    }
}

class ViewContactCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * {@code contactToView} set to the given contact,
     * and other fields set to their default value.
     */
    public ViewContactCommandResult(String feedbackToUser, Contact contactToView) {
        super(feedbackToUser, false, false, contactToView, false, false);
    }
}

class CloseViewPanelCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * {@code hideViewPanel} set to {@code true},
     * and other fields set to their default value.
     */
    public CloseViewPanelCommandResult(String feedbackToUser) {
        super(feedbackToUser, false, false, null, true, false);
    }
}

class ViewFilesCommandResult extends CommandResult {
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * {@code showFileList} set to {@code true},
     * and other fields set to their default value.
     */
    public ViewFilesCommandResult(String feedbackToUser) {
        super(feedbackToUser, false, false, null, false, true);
    }
}
