package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class AttendanceRecordTest {

    @Test
    public void constructor_emptyRecord_success() {
        AttendanceRecord record = new AttendanceRecord();
        assertTrue(record.isEmpty());
        assertTrue(record.getAllAttendances().isEmpty());
    }

    @Test
    public void constructor_withMap_success() {
        Map<Week, AttendanceStatus> attendances = new HashMap<>();
        attendances.put(new Week(1), AttendanceStatus.PRESENT);
        attendances.put(new Week(2), AttendanceStatus.ABSENT);

        AttendanceRecord record = new AttendanceRecord(attendances);
        assertFalse(record.isEmpty());
        assertEquals(2, record.getAllAttendances().size());
        assertEquals(AttendanceStatus.PRESENT, record.getAllAttendances().get(new Week(1)));
        assertEquals(AttendanceStatus.ABSENT, record.getAllAttendances().get(new Week(2)));
    }

    @Test
    public void markAttendance_newWeek_success() {
        AttendanceRecord record = new AttendanceRecord();
        Week week = new Week(1);
        AttendanceStatus status = AttendanceStatus.PRESENT;

        AttendanceRecord updatedRecord = record.markAttendance(week, status);

        // Original record should remain unchanged
        assertTrue(record.isEmpty());

        // Updated record should have the new attendance
        assertFalse(updatedRecord.isEmpty());
        assertEquals(AttendanceStatus.PRESENT, updatedRecord.getAllAttendances().get(week));
    }

    @Test
    public void markAttendance_existingWeek_updatesStatus() {
        Map<Week, AttendanceStatus> attendances = new HashMap<>();
        attendances.put(new Week(1), AttendanceStatus.PRESENT);
        AttendanceRecord record = new AttendanceRecord(attendances);

        // Update existing week
        AttendanceRecord updatedRecord = record.markAttendance(new Week(1), AttendanceStatus.ABSENT);

        assertEquals(AttendanceStatus.ABSENT, updatedRecord.getAllAttendances().get(new Week(1)));
        assertEquals(1, updatedRecord.getAllAttendances().size());
    }

    @Test
    public void markAttendance_multipleWeeks_success() {
        AttendanceRecord record = new AttendanceRecord();

        record = record.markAttendance(new Week(1), AttendanceStatus.PRESENT);
        record = record.markAttendance(new Week(2), AttendanceStatus.ABSENT);
        record = record.markAttendance(new Week(3), AttendanceStatus.PRESENT);

        assertEquals(3, record.getAllAttendances().size());
        assertEquals(AttendanceStatus.PRESENT, record.getAllAttendances().get(new Week(1)));
        assertEquals(AttendanceStatus.ABSENT, record.getAllAttendances().get(new Week(2)));
        assertEquals(AttendanceStatus.PRESENT, record.getAllAttendances().get(new Week(3)));
    }

    @Test
    public void getAttendanceStatus_existingWeek_returnsStatus() {
        Map<Week, AttendanceStatus> attendances = new HashMap<>();
        attendances.put(new Week(1), AttendanceStatus.PRESENT);
        AttendanceRecord record = new AttendanceRecord(attendances);

        assertEquals(AttendanceStatus.PRESENT, record.getAllAttendances().get(new Week(1)));
    }

    @Test
    public void getAttendanceStatus_nonExistentWeek_returnsNull() {
        AttendanceRecord record = new AttendanceRecord();
        assertTrue(record.getAllAttendances().get(new Week(1)) == null);
    }

    @Test
    public void getAllAttendances_returnsImmutableMap() {
        Map<Week, AttendanceStatus> attendances = new HashMap<>();
        attendances.put(new Week(1), AttendanceStatus.PRESENT);
        AttendanceRecord record = new AttendanceRecord(attendances);

        Map<Week, AttendanceStatus> allAttendances = record.getAllAttendances();

        // Should be immutable
        assertThrows(UnsupportedOperationException.class, () ->
            allAttendances.put(new Week(2), AttendanceStatus.ABSENT));
    }

    @Test
    public void isEmpty() {
        AttendanceRecord emptyRecord = new AttendanceRecord();
        assertTrue(emptyRecord.isEmpty());

        AttendanceRecord nonEmptyRecord = emptyRecord.markAttendance(new Week(1), AttendanceStatus.PRESENT);
        assertFalse(nonEmptyRecord.isEmpty());
    }

    @Test
    public void equals() {
        Map<Week, AttendanceStatus> attendances1 = new HashMap<>();
        attendances1.put(new Week(1), AttendanceStatus.PRESENT);
        attendances1.put(new Week(2), AttendanceStatus.ABSENT);

        Map<Week, AttendanceStatus> attendances2 = new HashMap<>();
        attendances2.put(new Week(1), AttendanceStatus.PRESENT);
        attendances2.put(new Week(2), AttendanceStatus.ABSENT);

        Map<Week, AttendanceStatus> attendances3 = new HashMap<>();
        attendances3.put(new Week(1), AttendanceStatus.PRESENT);

        AttendanceRecord record1 = new AttendanceRecord(attendances1);
        AttendanceRecord record2 = new AttendanceRecord(attendances2);
        AttendanceRecord record3 = new AttendanceRecord(attendances3);

        // Same object
        assertTrue(record1.equals(record1));

        // Same contents
        assertTrue(record1.equals(record2));

        // Different contents
        assertFalse(record1.equals(record3));

        // Different type
        assertFalse(record1.equals(attendances1));

        // Null
        assertFalse(record1.equals(null));
    }

    @Test
    public void testHashCode() {
        Map<Week, AttendanceStatus> attendances1 = new HashMap<>();
        attendances1.put(new Week(1), AttendanceStatus.PRESENT);
        attendances1.put(new Week(2), AttendanceStatus.ABSENT);

        Map<Week, AttendanceStatus> attendances2 = new HashMap<>();
        attendances2.put(new Week(1), AttendanceStatus.PRESENT);
        attendances2.put(new Week(2), AttendanceStatus.ABSENT);

        AttendanceRecord record1 = new AttendanceRecord(attendances1);
        AttendanceRecord record2 = new AttendanceRecord(attendances2);

        // Same contents should have same hash code
        assertEquals(record1.hashCode(), record2.hashCode());
    }

    @Test
    public void testToString() {
        AttendanceRecord record = new AttendanceRecord();
        String result = record.toString();
        assertTrue(result.contains("AttendanceRecord"));
        assertTrue(result.contains("attendanceMap={}"));

        record = record.markAttendance(new Week(1), AttendanceStatus.PRESENT);
        result = record.toString();
        assertTrue(result.contains("AttendanceRecord"));
        assertTrue(result.contains("Week 1=present"));

        record = record.markAttendance(new Week(2), AttendanceStatus.ABSENT);
        result = record.toString();
        assertTrue(result.contains("Week 1=present"));
        assertTrue(result.contains("Week 2=absent"));
    }
}
