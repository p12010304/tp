package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMOVE = new Prefix("c/");
    public static final Prefix PREFIX_CLEAR = new Prefix("ca/");
    public static final Prefix PREFIX_ON = new Prefix("on/");
    public static final Prefix PREFIX_BEFORE = new Prefix("before/");
    public static final Prefix PREFIX_AFTER = new Prefix("after/");

}
