package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import seedu.address.model.module.ModuleCode;
import seedu.address.testutil.PersonBuilder;

public class PersonStudentFieldsTest {

    @Test
    public void constructor_withoutStudentFields_setsNulls() {
        Person person = new PersonBuilder().build();
        assertNull(person.getStudentId());
        assertEquals(0, person.getModuleCodes().size());
    }

    @Test
    public void constructor_withStudentFields_setsValues() {
        String sid = "A0123456X";
        String mod = "CS2103T";
        Person person = new PersonBuilder()
                .withStudentId(sid)
                .withModuleCodes(mod)
                .build();

        assertEquals(new StudentId(sid), person.getStudentId());
        assertEquals(1, person.getModuleCodes().size());
        assertEquals(new ModuleCode(mod), person.getModuleCodes().iterator().next());
    }
}


