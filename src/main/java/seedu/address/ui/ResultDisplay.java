package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private WebView resultDisplay;

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
        // Check if message contains common error patterns
        boolean isError = isErrorMessage(message);

        StringBuilder formatted = new StringBuilder();

        // Only show header for errors
        if (isError) {
            formatted.append(ERROR_ICON).append(" ERROR\n");
            formatted.append(SEPARATOR).append("\n\n");
        } else {
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
