package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {

    @Test
    public void fromString_validStatus_success() {
        assertEquals(AttendanceStatus.PRESENT, AttendanceStatus.fromString("present"));
        assertEquals(AttendanceStatus.PRESENT, AttendanceStatus.fromString("Present"));
        assertEquals(AttendanceStatus.PRESENT, AttendanceStatus.fromString("PRESENT"));
        assertEquals(AttendanceStatus.ABSENT, AttendanceStatus.fromString("absent"));
        assertEquals(AttendanceStatus.ABSENT, AttendanceStatus.fromString("Absent"));
        assertEquals(AttendanceStatus.ABSENT, AttendanceStatus.fromString("ABSENT"));
    }

    @Test
    public void fromString_invalidStatus_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> AttendanceStatus.fromString("invalid"));
        assertThrows(IllegalArgumentException.class, () -> AttendanceStatus.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> AttendanceStatus.fromString("p"));
        assertThrows(IllegalArgumentException.class, () -> AttendanceStatus.fromString("a"));
    }

    @Test
    public void getValue() {
        assertEquals("present", AttendanceStatus.PRESENT.value);
        assertEquals("absent", AttendanceStatus.ABSENT.value);
    }

    @Test
    public void toString_returnsValue() {
        assertEquals("present", AttendanceStatus.PRESENT.toString());
        assertEquals("absent", AttendanceStatus.ABSENT.toString());
    }
}
