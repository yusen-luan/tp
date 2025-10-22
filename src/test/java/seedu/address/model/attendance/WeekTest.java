package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WeekTest {

    @Test
    public void constructor_validWeek_success() {
        // Test valid week numbers
        assertEquals(1, new Week(1).value);
        assertEquals(7, new Week(7).value);
        assertEquals(13, new Week(13).value);
    }

    @Test
    public void constructor_invalidWeek_throwsIllegalArgumentException() {
        // Test invalid week numbers
        assertThrows(IllegalArgumentException.class, () -> new Week(0));
        assertThrows(IllegalArgumentException.class, () -> new Week(14));
        assertThrows(IllegalArgumentException.class, () -> new Week(-1));
        assertThrows(IllegalArgumentException.class, () -> new Week(100));
    }

    @Test
    public void isValidWeek() {
        // Valid weeks
        assertTrue(Week.isValidWeek(1));
        assertTrue(Week.isValidWeek(7));
        assertTrue(Week.isValidWeek(13));

        // Invalid weeks
        assertFalse(Week.isValidWeek(0));
        assertFalse(Week.isValidWeek(14));
        assertFalse(Week.isValidWeek(-1));
        assertFalse(Week.isValidWeek(100));
    }

    @Test
    public void equals() {
        Week week1 = new Week(1);
        Week week2 = new Week(1);
        Week week3 = new Week(2);

        // Same object
        assertTrue(week1.equals(week1));

        // Same value
        assertTrue(week1.equals(week2));

        // Different value
        assertFalse(week1.equals(week3));

        // Different type
        assertFalse(week1.equals(1));

        // Null
        assertFalse(week1.equals(null));
    }

    @Test
    public void testHashCode() {
        Week week1 = new Week(1);
        Week week2 = new Week(1);
        Week week3 = new Week(2);

        // Same value should have same hash code
        assertEquals(week1.hashCode(), week2.hashCode());

        // Different value should have different hash code
        assertFalse(week1.hashCode() == week3.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("Week 1", new Week(1).toString());
        assertEquals("Week 7", new Week(7).toString());
        assertEquals("Week 13", new Week(13).toString());
    }
}
