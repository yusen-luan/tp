package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.attendance.Week;

public class JsonAdaptedAttendanceTest {

    @Test
    public void constructor_withWeekAndStatus_success() {
        JsonAdaptedAttendance adaptedAttendance = new JsonAdaptedAttendance(new Week(1), AttendanceStatus.PRESENT);
        // Test that the constructor works without errors
        assertTrue(adaptedAttendance instanceof JsonAdaptedAttendance);
    }

    @Test
    public void constructor_withAttendance_success() {
        Attendance attendance = new Attendance(new Week(2), AttendanceStatus.ABSENT);
        JsonAdaptedAttendance adaptedAttendance = new JsonAdaptedAttendance(attendance);
        // Test that the constructor works without errors
        assertTrue(adaptedAttendance instanceof JsonAdaptedAttendance);
    }

    @Test
    public void toModelType_validData_success() throws Exception {
        JsonAdaptedAttendance adaptedAttendance = new JsonAdaptedAttendance(1, "present");
        Attendance attendance = adaptedAttendance.toModelType();

        assertEquals(new Week(1), attendance.getWeek());
        assertEquals(AttendanceStatus.PRESENT, attendance.getStatus());
    }

    @Test
    public void toModelType_validAbsentData_success() throws Exception {
        JsonAdaptedAttendance adaptedAttendance = new JsonAdaptedAttendance(2, "absent");
        Attendance attendance = adaptedAttendance.toModelType();

        assertEquals(new Week(2), attendance.getWeek());
        assertEquals(AttendanceStatus.ABSENT, attendance.getStatus());
    }

    @Test
    public void toModelType_invalidWeek_throwsIllegalValueException() {
        JsonAdaptedAttendance adaptedAttendance = new JsonAdaptedAttendance(0, "present");
        assertThrows(IllegalValueException.class, () -> adaptedAttendance.toModelType());
    }

    @Test
    public void toModelType_invalidWeekHigh_throwsIllegalValueException() {
        JsonAdaptedAttendance adaptedAttendance = new JsonAdaptedAttendance(14, "present");
        assertThrows(IllegalValueException.class, () -> adaptedAttendance.toModelType());
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedAttendance adaptedAttendance = new JsonAdaptedAttendance(1, null);
        assertThrows(IllegalValueException.class, () -> adaptedAttendance.toModelType());
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedAttendance adaptedAttendance = new JsonAdaptedAttendance(1, "invalid");
        assertThrows(IllegalValueException.class, () -> adaptedAttendance.toModelType());
    }

    @Test
    public void toModelType_caseInsensitiveStatus_success() throws Exception {
        JsonAdaptedAttendance adaptedAttendance = new JsonAdaptedAttendance(1, "PRESENT");
        Attendance attendance = adaptedAttendance.toModelType();

        assertEquals(AttendanceStatus.PRESENT, attendance.getStatus());
    }

    @Test
    public void toModelType_mixedCaseStatus_success() throws Exception {
        JsonAdaptedAttendance adaptedAttendance = new JsonAdaptedAttendance(1, "Present");
        Attendance attendance = adaptedAttendance.toModelType();

        assertEquals(AttendanceStatus.PRESENT, attendance.getStatus());
    }
}
