package seedu.address.ui;

import javafx.scene.control.Label;
import seedu.address.model.tag.RankedTag;

/**
 * A {@code Label} that displays a ranked tag
 */
public class RankedTagLabel extends Label {

    /**
     * Constructs a new {@code RankedTagLabel} that displays a {@code RankedTag}.
     * @param rankedTag The {@code RankedTag} to display.
     */
    public RankedTagLabel(RankedTag rankedTag) {
        super(rankedTag.tagName + ':' + rankedTag.tagValue);
    }
}
