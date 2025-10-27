package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class AttendanceRecordTest {

    @Test
    public void markAttendance_presentWeek_recordsPresent() {
        AttendanceRecord record = new AttendanceRecord();
        Week week = new Week(1);

        AttendanceRecord updatedRecord = record.markAttendance(week, AttendanceStatus.PRESENT);

        assertTrue(updatedRecord.getAllAttendances().containsKey(week));
        assertEquals(AttendanceStatus.PRESENT, updatedRecord.getAllAttendances().get(week));
    }

    @Test
    public void markAttendance_absentWeek_recordsAbsent() {
        AttendanceRecord record = new AttendanceRecord();
        Week week = new Week(2);

        AttendanceRecord updatedRecord = record.markAttendance(week, AttendanceStatus.ABSENT);

        assertTrue(updatedRecord.getAllAttendances().containsKey(week));
        assertEquals(AttendanceStatus.ABSENT, updatedRecord.getAllAttendances().get(week));
    }

    @Test
    public void markAttendance_multipleWeeks_recordsAll() {
        AttendanceRecord record = new AttendanceRecord();

        AttendanceRecord updatedRecord = record.markAttendance(new Week(1), AttendanceStatus.PRESENT);
        updatedRecord = updatedRecord.markAttendance(new Week(2), AttendanceStatus.ABSENT);
        updatedRecord = updatedRecord.markAttendance(new Week(3), AttendanceStatus.PRESENT);

        assertEquals(3, updatedRecord.getAllAttendances().size());
        assertEquals(AttendanceStatus.PRESENT, updatedRecord.getAllAttendances().get(new Week(1)));
        assertEquals(AttendanceStatus.ABSENT, updatedRecord.getAllAttendances().get(new Week(2)));
        assertEquals(AttendanceStatus.PRESENT, updatedRecord.getAllAttendances().get(new Week(3)));
    }

    @Test
    public void isEmpty_noAttendance_returnsTrue() {
        AttendanceRecord record = new AttendanceRecord();
        assertTrue(record.isEmpty());
    }

    @Test
    public void isEmpty_withAttendance_returnsFalse() {
        AttendanceRecord record = new AttendanceRecord();
        record = record.markAttendance(new Week(1), AttendanceStatus.PRESENT);
        assertFalse(record.isEmpty());
    }

    @Test
    public void getAllAttendances_emptyRecord_returnsEmptyMap() {
        AttendanceRecord record = new AttendanceRecord();
        assertEquals(0, record.getAllAttendances().size());
    }

    @Test
    public void getAllAttendances_withRecords_returnsAllRecords() {
        AttendanceRecord record = new AttendanceRecord();
        record = record.markAttendance(new Week(1), AttendanceStatus.PRESENT);
        record = record.markAttendance(new Week(5), AttendanceStatus.ABSENT);

        Map<Week, AttendanceStatus> attendances = record.getAllAttendances();
        assertEquals(2, attendances.size());
        assertTrue(attendances.containsKey(new Week(1)));
        assertTrue(attendances.containsKey(new Week(5)));
    }

    @Test
    public void markAttendance_updateExistingWeek_overwritesPreviousStatus() {
        AttendanceRecord record = new AttendanceRecord();
        record = record.markAttendance(new Week(1), AttendanceStatus.PRESENT);
        record = record.markAttendance(new Week(1), AttendanceStatus.ABSENT);

        assertEquals(AttendanceStatus.ABSENT, record.getAllAttendances().get(new Week(1)));
    }

    @Test
    public void markAttendance_allWeeks_recordsAllWeeks() {
        AttendanceRecord record = new AttendanceRecord();

        for (int week = 1; week <= 13; week++) {
            AttendanceStatus status = week % 2 == 0 ? AttendanceStatus.ABSENT : AttendanceStatus.PRESENT;
            record = record.markAttendance(new Week(week), status);
        }

        assertEquals(13, record.getAllAttendances().size());
        assertEquals(AttendanceStatus.PRESENT, record.getAllAttendances().get(new Week(1)));
        assertEquals(AttendanceStatus.ABSENT, record.getAllAttendances().get(new Week(2)));
        assertEquals(AttendanceStatus.PRESENT, record.getAllAttendances().get(new Week(13)));
    }
}
