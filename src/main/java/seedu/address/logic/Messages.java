package seedu.address.logic;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.grade.Grade;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The student index provided is invalid.";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "✓ %1$d students listed.";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName());

        // Only show phone if it exists (not a student)
        if (person.getPhone() != null) {
            builder.append("; Phone: ").append(person.getPhone());
        }

        builder.append("; Email: ").append(person.getEmail());

        // Only show address if it exists (not a student)
        if (person.getAddress() != null) {
            builder.append("; Address: ").append(person.getAddress());
        }

        if (person.getStudentId() != null) {
            builder.append("; Student ID: ").append(person.getStudentId());
        }
        if (!person.getModuleCodes().isEmpty()) {
            builder.append("; Module Codes: ");
            person.getModuleCodes().forEach(mc -> builder.append(mc).append(" "));
        }
        builder.append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    // ========== Enhanced Formatting Utilities ==========

    /**
     * Returns a checkmark symbol for success messages.
     */
    public static String checkmark() {
        return "\u2713"; // ✓
    }

    /**
     * Returns a cross mark symbol for error/absent status.
     */
    public static String crossmark() {
        return "\u2717"; // ✗
    }

    /**
     * Formats a bullet point for list items.
     */
    public static String bulletPoint(String item) {
        return "  \u2022 " + item; // •
    }

    /**
     * Formats a section header.
     */
    public static String sectionHeader(String title) {
        return "=== " + title + " ===";
    }

    /**
     * Formats a student identifier as "Name (StudentID)".
     * For non-students, returns just the name.
     */
    public static String formatStudentId(Person person) {
        if (person.getStudentId() != null) {
            return person.getName() + " (" + person.getStudentId() + ")";
        }
        return person.getName().toString();
    }

    /**
     * Formats a collection of grades as a bulleted list.
     * Example output:
     *   • Midterm: 85
     *   • Assignment1: 92
     */
    public static String formatGrades(Collection<Grade> grades) {
        if (grades.isEmpty()) {
            return "";
        }
        return grades.stream()
                .sorted((g1, g2) -> g1.assignmentName.compareToIgnoreCase(g2.assignmentName))
                .map(g -> bulletPoint(g.assignmentName + ": " + g.score))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Formats a collection of tags as a space-separated list.
     * Example output: [Tag1] [Tag2] [Tag3]
     */
    public static String formatTags(Collection<Tag> tags) {
        if (tags.isEmpty()) {
            return "";
        }
        return tags.stream()
                .map(Tag::toString)
                .collect(Collectors.joining(" "));
    }

    /**
     * Formats a collection of strings as a bulleted list.
     */
    public static String formatBulletList(Collection<String> items) {
        if (items.isEmpty()) {
            return "";
        }
        return items.stream()
                .map(Messages::bulletPoint)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Formats a map of changes as a bulleted list showing field: new value.
     * Example output:
     *   • Email: newemail@example.com
     *   • Phone: 98765432
     */
    public static String formatChanges(Map<String, String> changes) {
        if (changes.isEmpty()) {
            return "";
        }
        return changes.entrySet().stream()
                .map(entry -> bulletPoint(entry.getKey() + ": " + entry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Creates a success message with checkmark.
     * Example: "✓ Added student: John Doe (A0123456X)"
     */
    public static String successMessage(String action) {
        return checkmark() + " " + action;
    }

    /**
     * Formats a count with singular/plural handling.
     * Example: formatCount(1, "student") -> "1 student"
     *          formatCount(5, "student") -> "5 students"
     */
    public static String formatCount(int count, String singularNoun) {
        if (count == 1) {
            return count + " " + singularNoun;
        }
        return count + " " + singularNoun + "s";
    }

}
