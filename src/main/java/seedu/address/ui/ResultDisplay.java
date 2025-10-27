package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";
    private static final String SUCCESS_ICON = "✓";
    private static final String ERROR_ICON = "✗";
    private static final String INFO_ICON = "ℹ";
    private static final String SEPARATOR = "────────────────────────────────────────────────";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    /**
     * Sets the feedback message with enhanced formatting.
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        String formatted = formatMessage(feedbackToUser);
        resultDisplay.setText(formatted);
    }

    /**
     * Formats the message with markdown-style enhancements for better readability.
     */
    private String formatMessage(String message) {
        // Check if message contains common success/error patterns
        boolean isSuccess = isSuccessMessage(message);
        boolean isError = isErrorMessage(message);

        StringBuilder formatted = new StringBuilder();

        // Add icon and header based on message type
        if (isSuccess) {
            formatted.append(SUCCESS_ICON).append(" SUCCESS\n");
            formatted.append(SEPARATOR).append("\n\n");
        } else if (isError) {
            formatted.append(ERROR_ICON).append(" ERROR\n");
            formatted.append(SEPARATOR).append("\n\n");
        } else {
            formatted.append(INFO_ICON).append(" INFO\n");
            formatted.append(SEPARATOR).append("\n\n");
        }

        // Format the main message content
        String[] lines = message.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            if (line.isEmpty()) {
                formatted.append("\n");
                continue;
            }

            // Add bullet points for list items
            if (line.matches("^[\\d]+\\..*")) {
                // Numbered list
                formatted.append("  ").append(line).append("\n");
            } else if (line.startsWith("-") || line.startsWith("*")) {
                // Bullet list
                formatted.append("  ").append(line).append("\n");
            } else if (line.contains(":")) {
                // Key-value pairs - make them stand out
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    formatted.append("▸ ").append(parts[0].trim()).append(":\n");
                    formatted.append("  ").append(parts[1].trim()).append("\n");
                } else {
                    formatted.append("  ").append(line).append("\n");
                }
            } else {
                formatted.append("  ").append(line).append("\n");
            }
        }

        formatted.append("\n").append(SEPARATOR);

        return formatted.toString();
    }

    /**
     * Checks if the message indicates success.
     */
    private boolean isSuccessMessage(String message) {
        String lower = message.toLowerCase();
        return lower.contains("added")
                || lower.contains("deleted")
                || lower.contains("edited")
                || lower.contains("cleared")
                || lower.contains("successfully")
                || lower.contains("listed")
                || (lower.contains("found") && !lower.contains("not found"));
    }

    /**
     * Checks if the message indicates an error.
     */
    private boolean isErrorMessage(String message) {
        String lower = message.toLowerCase();
        return lower.contains("error")
                || lower.contains("invalid")
                || lower.contains("unknown")
                || lower.contains("missing")
                || lower.contains("not found")
                || lower.contains("failed")
                || lower.contains("incorrect");
    }

}
