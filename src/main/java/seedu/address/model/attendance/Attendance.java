package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an attendance record for a specific week.
 * Guarantees: immutable; week and status are present and not null.
 */
public class Attendance {

    private final Week week;
    private final AttendanceStatus status;

    /**
     * Constructs an {@code Attendance}.
     *
     * @param week A valid week.
     * @param status A valid attendance status.
     */
    public Attendance(Week week, AttendanceStatus status) {
        requireNonNull(week);
        requireNonNull(status);
        this.week = week;
        this.status = status;
    }

    public Week getWeek() {
        return week;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Attendance)) {
            return false;
        }

        Attendance otherAttendance = (Attendance) other;
        return week.equals(otherAttendance.week)
                && status.equals(otherAttendance.status);
    }

    @Override
    public int hashCode() {
        return week.hashCode() + status.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("week", week)
                .add("status", status)
                .toString();
    }
}
