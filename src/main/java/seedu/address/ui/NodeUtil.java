package seedu.address.ui;

import javafx.scene.Node;

/**
 * Utility functions for {@code Node}.
 */
public interface NodeUtil {
    /**
     * Hides a node by setting its {@code visible} and {@code managed} attributes to {@code false}.
     */
    public static void hide(Node node) {
        node.setVisible(false);
        node.setManaged(false);
    }

    /**
     * Shows a node by setting its {@code visible} and {@code managed} attributes to {@code true}.
     */
    public static void show(Node node) {
        node.setVisible(true);
        node.setManaged(true);
    }
}
