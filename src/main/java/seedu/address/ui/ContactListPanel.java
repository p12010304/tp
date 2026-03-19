package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contact.Contact;

/**
 * Panel containing the list of contacts.
 */
public class ContactListPanel extends UiPart<Region> {
    private static final String FXML = "ContactListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ContactListPanel.class);

    @FXML
    private ListView<Contact> contactListView;

    private ScrollBar scrollBar;

    /**
     * Creates a {@code ContactListPanel} with the given {@code ObservableList}.
     */
    public ContactListPanel(ObservableList<Contact> contactList) {
        super(FXML);
        contactListView.setItems(contactList);
        contactListView.setCellFactory(listView -> new ContactListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Contact} using a {@code ContactCard}.
     */
    class ContactListViewCell extends ListCell<Contact> {
        @Override
        protected void updateItem(Contact contact, boolean empty) {
            super.updateItem(contact, empty);

            if (empty || contact == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ContactCard(contact, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Sets up the custom scroll bar functions of {@code ContactListPanel};
     */
    public void setUp() {
        for (Node node : contactListView.lookupAll(".scroll-bar")) {
            if (node instanceof ScrollBar) {
                final ScrollBar bar = (ScrollBar) node;
                if (bar.getOrientation().equals(Orientation.VERTICAL)) {
                    scrollBar = bar;
                }
            }
        }
    }

    /**
     * Scrolls the list to the top.
     */
    public void scrollToTop() {
        if (scrollBar == null) {
            setUp();
        }
        if (scrollBar != null) {
            scrollBar.setValue(scrollBar.getMin());
        }
    }

    /**
     * Scrolls the list to the bottom.
     */
    public void scrollToBottom() {
        if (scrollBar == null) {
            setUp();
        }
        if (scrollBar != null) {
            scrollBar.setValue(scrollBar.getMax());
        }
    }
}
