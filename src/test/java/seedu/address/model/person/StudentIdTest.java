package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentIdTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentId(null));
    }

    @Test
    public void constructor_invalidStudentId_throwsIllegalArgumentException() {
        String invalidStudentId = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentId(invalidStudentId));
    }

    @Test
    public void isValidStudentId() {
        // null student ID
        assertThrows(NullPointerException.class, () -> StudentId.isValidStudentId(null));

        // invalid student IDs - test boundary cases
        assertFalse(StudentId.isValidStudentId("")); // empty string
        assertFalse(StudentId.isValidStudentId(" ")); // spaces only
        assertFalse(StudentId.isValidStudentId("A012345X")); // only 6 digits (too few)
        assertFalse(StudentId.isValidStudentId("A01234567X")); // 8 digits (too many)
        assertFalse(StudentId.isValidStudentId("a0123456X")); // lowercase 'a'
        assertFalse(StudentId.isValidStudentId("A0123456x")); // lowercase last letter
        assertFalse(StudentId.isValidStudentId("B0123456X")); // doesn't start with 'A'
        assertFalse(StudentId.isValidStudentId("A012345")); // missing last letter
        assertFalse(StudentId.isValidStudentId("0123456X")); // missing 'A' prefix

        // valid student IDs - test boundary cases
        assertTrue(StudentId.isValidStudentId("A0123456X")); // standard format
        assertTrue(StudentId.isValidStudentId("A0000000A")); // all zeros
        assertTrue(StudentId.isValidStudentId("A9999999Z")); // all nines
        assertTrue(StudentId.isValidStudentId("A0234567Y")); // from your spec
    }

    @Test
    public void equals() {
        StudentId studentId = new StudentId("A0123456X");

        // same values -> returns true
        assertTrue(studentId.equals(new StudentId("A0123456X")));

        // same object -> returns true
        assertTrue(studentId.equals(studentId));

        // null -> returns false
        assertFalse(studentId.equals(null));

        // different types -> returns false
        assertFalse(studentId.equals(5.0f));

        // different values -> returns false
        assertFalse(studentId.equals(new StudentId("A0234567Y")));
    }
}
