package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a student's attendance record across multiple weeks.
 * Guarantees: immutable; week and status are present and not null.
 */
public class AttendanceRecord {

    private final Map<Week, AttendanceStatus> attendanceMap;

    /**
     * Constructs an empty {@code AttendanceRecord}.
     */
    public AttendanceRecord() {
        this.attendanceMap = new HashMap<>();
    }

    /**
     * Constructs an {@code AttendanceRecord} with the given attendance data.
     *
     * @param attendanceMap A map of week to attendance status.
     */
    public AttendanceRecord(Map<Week, AttendanceStatus> attendanceMap) {
        requireNonNull(attendanceMap);
        this.attendanceMap = new HashMap<>(attendanceMap);
    }

    /**
     * Marks attendance for a specific week.
     *
     * @param week The week to mark attendance for.
     * @param status The attendance status.
     * @return A new AttendanceRecord with the updated attendance.
     */
    public AttendanceRecord markAttendance(Week week, AttendanceStatus status) {
        requireNonNull(week);
        requireNonNull(status);
        
        Map<Week, AttendanceStatus> newMap = new HashMap<>(attendanceMap);
        newMap.put(week, status);
        return new AttendanceRecord(newMap);
    }

    /**
     * Gets the attendance status for a specific week.
     *
     * @param week The week to get attendance for.
     * @return The attendance status, or null if not recorded.
     */
    public AttendanceStatus getAttendance(Week week) {
        requireNonNull(week);
        return attendanceMap.get(week);
    }

    /**
     * Returns an immutable view of all attendance records.
     *
     * @return An immutable map of week to attendance status.
     */
    public Map<Week, AttendanceStatus> getAllAttendances() {
        return Collections.unmodifiableMap(attendanceMap);
    }

    /**
     * Returns true if attendance is recorded for the given week.
     *
     * @param week The week to check.
     * @return True if attendance is recorded for the week.
     */
    public boolean hasAttendance(Week week) {
        requireNonNull(week);
        return attendanceMap.containsKey(week);
    }

    /**
     * Returns the number of weeks with recorded attendance.
     *
     * @return The number of weeks with attendance records.
     */
    public int size() {
        return attendanceMap.size();
    }

    /**
     * Returns true if the attendance record is empty.
     *
     * @return True if no attendance is recorded.
     */
    public boolean isEmpty() {
        return attendanceMap.isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AttendanceRecord)) {
            return false;
        }

        AttendanceRecord otherRecord = (AttendanceRecord) other;
        return attendanceMap.equals(otherRecord.attendanceMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendanceMap);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("attendanceMap", attendanceMap)
                .toString();
    }
}
