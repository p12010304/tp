package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ParserTest {
    @Test
    public void isIntegerTest() {
        assertTrue(Parser.isInteger("123"));
        assertFalse(Parser.isInteger("4.5"));
        assertFalse(Parser.isInteger("abc"));
        assertFalse(Parser.isInteger(""));
        assertFalse(Parser.isInteger(null));
    }

    @Test
    public void isIntegerArrayTest() {
        assertTrue(Parser.isIntegerArray(new String[] {"123", "456", "789"}));
        assertFalse(Parser.isIntegerArray(new String[] {"123", "4.5", "789"}));
        assertTrue(Parser.isIntegerArray(new String[] {}));
        assertFalse(Parser.isIntegerArray(new String[] {"abc", "def", "ghi"}));
        assertFalse(Parser.isIntegerArray(new String[] {"", ""}));
    }

    @Test
    public void replaceStringWithArraySelection() {
        String sample = "This is a sample string. THIS IS A SAMPLE STRING. THis Is A saMplE STRIng.";
        String[] stringsToReplace = new String[] {"This", "SAMPLE", "STRIng"};
        String replacement = "that";
        String target = "that is a sample string. THIS IS A that STRING. THis Is A saMplE that.";
        assertEquals(target, Parser.replaceStringWithArraySelection(sample, stringsToReplace, replacement));
    }
}
