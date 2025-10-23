package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    public void constructor_validParameters_success() {
        Week week = new Week(1);
        AttendanceStatus status = AttendanceStatus.PRESENT;
        Attendance attendance = new Attendance(week, status);

        assertEquals(week, attendance.getWeek());
        assertEquals(status, attendance.getStatus());
    }

    @Test
    public void constructor_nullWeek_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Attendance(null, AttendanceStatus.PRESENT));
    }

    @Test
    public void constructor_nullStatus_throwsNullPointerException() {
        Week week = new Week(1);
        assertThrows(NullPointerException.class, () -> new Attendance(week, null));
    }

    @Test
    public void equals() {
        Week week1 = new Week(1);
        Week week2 = new Week(1);
        Week week3 = new Week(2);
        AttendanceStatus status1 = AttendanceStatus.PRESENT;
        AttendanceStatus status2 = AttendanceStatus.PRESENT;
        AttendanceStatus status3 = AttendanceStatus.ABSENT;

        Attendance attendance1 = new Attendance(week1, status1);
        Attendance attendance2 = new Attendance(week2, status2);
        Attendance attendance3 = new Attendance(week1, status3);
        Attendance attendance4 = new Attendance(week3, status1);

        // Same object
        assertTrue(attendance1.equals(attendance1));

        // Same week and status
        assertTrue(attendance1.equals(attendance2));

        // Different status, same week
        assertFalse(attendance1.equals(attendance3));

        // Different week, same status
        assertFalse(attendance1.equals(attendance4));

        // Different type
        assertFalse(attendance1.equals(week1));

        // Null
        assertFalse(attendance1.equals(null));
    }

    @Test
    public void testHashCode() {
        Week week1 = new Week(1);
        Week week2 = new Week(1);
        AttendanceStatus status1 = AttendanceStatus.PRESENT;
        AttendanceStatus status2 = AttendanceStatus.PRESENT;
        AttendanceStatus status3 = AttendanceStatus.ABSENT;

        Attendance attendance1 = new Attendance(week1, status1);
        Attendance attendance2 = new Attendance(week2, status2);
        Attendance attendance3 = new Attendance(week1, status3);

        // Same week and status should have same hash code
        assertEquals(attendance1.hashCode(), attendance2.hashCode());

        // Different status should have different hash code
        assertFalse(attendance1.hashCode() == attendance3.hashCode());
    }

    @Test
    public void testToString() {
        Week week = new Week(1);
        AttendanceStatus status = AttendanceStatus.PRESENT;
        Attendance attendance = new Attendance(week, status);

        String result = attendance.toString();
        assertTrue(result.contains("Attendance"));
        assertTrue(result.contains("week=Week 1"));
        assertTrue(result.contains("status=present"));

        Week week2 = new Week(2);
        AttendanceStatus status2 = AttendanceStatus.ABSENT;
        Attendance attendance2 = new Attendance(week2, status2);

        result = attendance2.toString();
        assertTrue(result.contains("Attendance"));
        assertTrue(result.contains("week=Week 2"));
        assertTrue(result.contains("status=absent"));
    }
}
