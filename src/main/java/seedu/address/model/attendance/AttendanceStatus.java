package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;

/**
 * Represents the attendance status of a student for a specific week.
 * UNMARK is a special status used to remove an existing attendance record.
 */
public enum AttendanceStatus {
    PRESENT("present"),
    ABSENT("absent"),
    UNMARK("unmark"); // Special status to remove attendance record

    public final String value;

    AttendanceStatus(String value) {
        this.value = value;
    }

    /**
     * Returns the AttendanceStatus corresponding to the given string.
     * @param statusString the string representation of the status
     * @return the corresponding AttendanceStatus
     * @throws IllegalArgumentException if the string is not a valid status
     */
    public static AttendanceStatus fromString(String statusString) {
        requireNonNull(statusString);
        String trimmed = statusString.trim().toLowerCase();

        for (AttendanceStatus status : values()) {
            if (status.value.equals(trimmed)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Invalid attendance status: " + statusString);
    }

    @Override
    public String toString() {
        return value;
    }
}
