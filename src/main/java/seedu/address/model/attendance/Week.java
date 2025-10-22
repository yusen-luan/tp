package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Week in the attendance system.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeek(int)}
 */
public class Week {

    public static final String MESSAGE_CONSTRAINTS =
            "Week should be a number between 1 and 13 (inclusive)";
    public static final String VALIDATION_REGEX = "^(1[0-3]|[1-9])$";
    public final int value;

    /**
     * Constructs a {@code Week}.
     *
     * @param week A valid week number.
     */
    public Week(int week) {
        requireNonNull(week);
        checkArgument(isValidWeek(week), MESSAGE_CONSTRAINTS);
        this.value = week;
    }

    /**
     * Returns true if a given integer is a valid week number.
     */
    public static boolean isValidWeek(int test) {
        return test >= 1 && test <= 13;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Week)) {
            return false;
        }

        Week otherWeek = (Week) other;
        return value == otherWeek.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "Week " + value;
    }

}
