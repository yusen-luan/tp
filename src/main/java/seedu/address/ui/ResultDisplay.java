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

    @FXML
    private TextArea resultDisplay;

    /**
     * Creates a ResultDisplay with plain text formatting.
     */
    public ResultDisplay() {
        super(FXML);
        resultDisplay.setEditable(false);
        resultDisplay.setWrapText(true);
        showWelcomeMessage();
    }

    /**
     * Shows the welcome message on startup.
     */
    private void showWelcomeMessage() {
        String welcomeMessage = "✓ Welcome to TeachMate!\n\n"
                + "This result display shows feedback for your commands, including confirmations, "
                + "warnings, and detailed results.\n\n"
                + "Available commands:\n"
                + "  • add - Add a new student\n"
                + "  • delete - Delete a student\n"
                + "  • edit - Edit student details\n"
                + "  • list - List all students\n"
                + "  • find - Find students by name\n"
                + "  • filter - Filter students by tags\n"
                + "  • view - View detailed student information\n"
                + "  • attendance - Mark attendance\n"
                + "  • grade - Add or update grades\n"
                + "  • deletegrade - Delete a grade\n"
                + "  • remark - Add remarks to a student\n"
                + "  • tag - Add tags to a student\n"
                + "  • untag - Remove tags from a student\n"
                + "  • clear - Clear all entries\n"
                + "  • help - Show help window\n"
                + "  • exit - Exit the application\n\n"
                + "Type 'help' for more detailed information.";
        setFeedbackToUser(welcomeMessage);
    }

    /**
     * Sets the feedback message to display to the user.
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    /**
     * Sets the theme for the result display.
     * Note: Theme switching is handled by CSS, this method is kept for compatibility.
     * @param isDark true for dark theme, false for light theme
     */
    public void setTheme(boolean isDark) {
        // Theme styling is handled by CSS in DarkTheme.css and LightTheme.css
        // This method is kept for compatibility with MainWindow theme switching
    }
}
