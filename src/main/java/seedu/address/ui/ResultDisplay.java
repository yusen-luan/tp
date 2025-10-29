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

    /**
     * Creates a ResultDisplay with GitHub-style HTML formatting support.
     */
    public ResultDisplay() {
        super(FXML);
        // Disable context menu on WebView
        resultDisplay.setContextMenuEnabled(false);
    }

    /**
     * Sets the feedback message with GitHub-style rich HTML formatting.
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        String html = convertToHtml(feedbackToUser);
        resultDisplay.getEngine().loadContent(html);
    }

    /**
     * Converts plain text message to GitHub-style HTML with rich formatting.
     */
    private String convertToHtml(String message) {
        boolean isError = isErrorMessage(message);
        boolean isSuccess = message.contains("✓");

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head>");
        html.append("<style>");
        html.append(getGitHubStyleCss());
        html.append("</style></head><body>");

        // Message container
        html.append("<div class='message-container ");
        html.append(isError ? "error" : (isSuccess ? "success" : "info"));
        html.append("'>");

        // Header with icon
        if (isError) {
            html.append("<div class='message-header error-header'>");
            html.append("<span class='icon'>✗</span>");
            html.append("<span class='header-text'>Error</span>");
            html.append("</div>");
        } else if (isSuccess) {
            html.append("<div class='message-header success-header'>");
            html.append("<span class='icon'>✓</span>");
            html.append("<span class='header-text'>Success</span>");
            html.append("</div>");
        }

        // Message body
        html.append("<div class='message-body'>");
        html.append(formatMessageContent(message));
        html.append("</div>");

        html.append("</div></body></html>");
        return html.toString();
    }

    /**
     * Formats the message content with proper HTML structure.
     */
    private String formatMessageContent(String message) {
        StringBuilder content = new StringBuilder();
        String[] lines = message.split("\n");

        for (String line : lines) {
            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            // Skip the checkmark if it's at the start (already in header)
            if (line.startsWith("✓")) {
                line = line.substring(1).trim();
            }

            // Bullet points
            if (line.startsWith("•")) {
                if (!content.toString().contains("<ul>")) {
                    content.append("<ul class='bullet-list'>");
                }
                content.append("<li>").append(escapeHtml(line.substring(1).trim())).append("</li>");
            } else {
                // Close any open list
                if (content.toString().endsWith("</li>")) {
                    content.append("</ul>");
                }

                // Regular paragraph with highlighting for student IDs and values
                String formatted = highlightImportantParts(line);
                content.append("<p>").append(formatted).append("</p>");
            }
        }

        // Close any open list
        if (content.toString().contains("<ul>") && !content.toString().endsWith("</ul>")) {
            content.append("</ul>");
        }

        return content.toString();
    }

    /**
     * Highlights important parts like student IDs, module codes, and numbers.
     */
    private String highlightImportantParts(String text) {
        text = escapeHtml(text);

        // Highlight student IDs (A0123456X format)
        text = text.replaceAll("(A\\d{7}[A-Z])", "<span class='student-id'>$1</span>");

        // Highlight module codes (CS2103T format)
        text = text.replaceAll("([A-Z]{2,3}\\d{4}[A-Z]?)", "<span class='module-code'>$1</span>");

        // Highlight numbers (grades, counts)
        text = text.replaceAll("\\b(\\d+)\\b", "<span class='number'>$1</span>");

        // Highlight tags in brackets
        text = text.replaceAll("\\[([^\\]]+)\\]", "<span class='tag'>[$1]</span>");

        return text;
    }

    /**
     * Escapes HTML special characters.
     */
    private String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
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
                || lower.contains("cannot")
                || lower.contains("failed");
    }

    /**
     * Returns GitHub-style CSS for rich message formatting.
     */
    private String getGitHubStyleCss() {
        return "body { "
                + "margin: 0; "
                + "padding: 8px; "
                + "font-family: -apple-system, BlinkMacSystemFont, 'SF Pro Display', "
                + "'Segoe UI', 'Noto Sans', Helvetica, Arial, sans-serif; "
                + "font-size: 13px; "
                + "line-height: 1.5; "
                + "color: rgba(255, 255, 255, 0.92); "
                + "background: transparent; "
                + "} "
                + ".message-container { "
                + "border-radius: 12px; "
                + "border: 1px solid rgba(255, 255, 255, 0.1); "
                + "background: rgba(28, 28, 30, 0.6); "
                + "overflow: hidden; "
                + "box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3); "
                + "} "
                + ".message-header { "
                + "padding: 10px 14px; "
                + "font-weight: 600; "
                + "font-size: 14px; "
                + "display: flex; "
                + "align-items: center; "
                + "gap: 6px; "
                + "border-bottom: 1px solid rgba(255, 255, 255, 0.08); "
                + "} "
                + ".success-header { "
                + "background: linear-gradient(135deg, #34C759 0%, #30D158 100%); "
                + "color: #ffffff; "
                + "} "
                + ".error-header { "
                + "background: linear-gradient(135deg, #FF453A 0%, #FF6961 100%); "
                + "color: #ffffff; "
                + "} "
                + ".icon { "
                + "font-size: 16px; "
                + "} "
                + ".message-body { "
                + "padding: 12px 14px; "
                + "} "
                + "p { "
                + "margin: 0 0 8px 0; "
                + "} "
                + "p:last-child { "
                + "margin-bottom: 0; "
                + "} "
                + ".bullet-list { "
                + "margin: 6px 0; "
                + "padding-left: 20px; "
                + "} "
                + ".bullet-list li { "
                + "margin: 4px 0; "
                + "} "
                + ".student-id { "
                + "font-weight: 600; "
                + "color: #0A84FF; "
                + "font-family: 'SF Mono', Monaco, 'Cascadia Code', Consolas, monospace; "
                + "font-size: 12px; "
                + "background: rgba(10, 132, 255, 0.15); "
                + "padding: 2px 5px; "
                + "border-radius: 4px; "
                + "} "
                + ".module-code { "
                + "font-weight: 600; "
                + "color: #AF52DE; "
                + "font-family: 'SF Mono', Monaco, 'Cascadia Code', Consolas, monospace; "
                + "font-size: 12px; "
                + "background: rgba(175, 82, 222, 0.15); "
                + "padding: 2px 5px; "
                + "border-radius: 4px; "
                + "} "
                + ".number { "
                + "font-weight: 600; "
                + "color: #64D2FF; "
                + "} "
                + ".tag { "
                + "display: inline-block; "
                + "padding: 2px 7px; "
                + "margin: 0 3px; "
                + "font-size: 11px; "
                + "font-weight: 500; "
                + "background: rgba(10, 132, 255, 0.15); "
                + "color: #0A84FF; "
                + "border-radius: 10px; "
                + "border: 1px solid rgba(10, 132, 255, 0.3); "
                + "}";
    }

}
