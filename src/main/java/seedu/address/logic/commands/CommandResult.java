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

    /** Whether to hide the contact detail panel. */
    private final boolean hideContactDetail;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, null, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified fields including contact to view.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, Contact contactToView) {
        this(feedbackToUser, showHelp, exit, contactToView, false);
    }

    /**
     * Constructs a {@code CommandResult} with all fields specified.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
            Contact contactToView, boolean hideContactDetail) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.contactToView = contactToView;
        this.hideContactDetail = hideContactDetail;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, null, false);
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
    public boolean isHideContactDetail() {
        return hideContactDetail;
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
                && hideContactDetail == otherCommandResult.hideContactDetail;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, contactToView, hideContactDetail);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("contactToView", contactToView)
                .add("hideContactDetail", hideContactDetail)
                .toString();
    }

}
