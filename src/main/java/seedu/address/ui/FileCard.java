package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.timepoint.DateTimeUtil;

/**
 * A UI component that displays information of a B2B4U file.
 */
public class FileCard extends UiPart<Region> {
    private static final String FXML = "FileListCard.fxml";

    @FXML
    private Label name;
    @FXML
    private Label lastModified;
    @FXML
    private Label contactCount;

    /**
     * Creates a {@code FileCard} with the given file to display.
     */
    public FileCard(FileCardDetails details) {
        super(FXML);
        name.setText(details.name());
        lastModified.setText("Last modified: " + DateTimeUtil.toDisplayString(details.lastModifiedTime()));
        contactCount.setText(details.contactCount() + "");
    }
}
