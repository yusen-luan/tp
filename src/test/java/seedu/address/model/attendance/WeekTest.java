package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class WeekTest {

    @Test
    public void constructor_validWeek_createsWeekSuccessfully() {
        Week week = new Week(1);
        assertEquals(1, week.value);
    }

    @Test
    public void constructor_maxWeek_createsWeekSuccessfully() {
        Week week = new Week(13);
        assertEquals(13, week.value);
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        Week week1 = new Week(5);
        Week week2 = new Week(5);
        assertEquals(week1, week2);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        Week week1 = new Week(3);
        Week week2 = new Week(4);
        assertNotEquals(week1, week2);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Week week = new Week(7);
        assertTrue(week.equals(week));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Week week = new Week(2);
        assertFalse(week.equals("2"));
    }

    @Test
    public void hashCode_sameValue_sameHashCode() {
        Week week1 = new Week(6);
        Week week2 = new Week(6);
        assertEquals(week1.hashCode(), week2.hashCode());
    }

    @Test
    public void hashCode_differentValue_differentHashCode() {
        Week week1 = new Week(1);
        Week week2 = new Week(2);
        assertNotEquals(week1.hashCode(), week2.hashCode());
    }

    @Test
    public void toString_validWeek_returnsCorrectFormat() {
        Week week = new Week(8);
        assertTrue(week.toString().contains("8"));
    }

    @Test
    public void week_allValidWeeks_createdSuccessfully() {
        for (int i = 1; i <= 13; i++) {
            Week week = new Week(i);
            assertEquals(i, week.value);
        }
    }
}
