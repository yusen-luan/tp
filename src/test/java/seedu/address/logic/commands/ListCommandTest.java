package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private static final Person STUDENT1 = new PersonBuilder()
            .withName("Alice Tan")
            .withPhone("81234567")
            .withEmail("alice.tan@example.com")
            .withAddress("Kent Ridge Hall")
            .withTags("friends")
            .withStudentId("A0123457X")
            .withModuleCode("CS2103T") // target module
            .build();

    private static final Person STUDENT2 = new PersonBuilder()
            .withName("Bob Lim")
            .withPhone("81234568")
            .withEmail("bob.lim@example.com")
            .withAddress("Prince George’s Park")
            .withTags("friends")
            .withStudentId("A0123456X")
            .withModuleCode("CS2100") // different module
            .build();

    private static final Person STUDENT3 = new PersonBuilder()
            .withName("Chen Ding Dong")
            .withPhone("81234568")
            .withEmail("bob.lim@example.com")
            .withAddress("Prince George’s Park")
            .withTags("friends")
            .withStudentId("A0123458X")
            .withModuleCode("CS2100") // different module
            .build();

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }


    @Test
    public void execute_validModuleCode_showsFilteredList() {
        AddressBook ab = new AddressBook();
        ab.addPerson(STUDENT1);
        ab.addPerson(STUDENT2);
        ab.addPerson(STUDENT3);

        model = new ModelManager(ab, new UserPrefs());
        expectedModel = new ModelManager(ab, new UserPrefs());

        ModuleCode validModuleCode = new ModuleCode("CS2103T");
        //actual list command output
        ListCommand listCommand = new ListCommand(validModuleCode);

        //expected list command output
        Predicate<Person> modulePredicate = person -> person.getModuleCodes().stream()
                .anyMatch(mc -> mc.equals(validModuleCode));

        expectedModel.updateFilteredPersonList(modulePredicate);
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_MODULE, validModuleCode);

        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_moduleWithNoStudents_showsNoStudentsMessage() {
        AddressBook ab = new AddressBook();
        ab.addPerson(STUDENT1);
        ab.addPerson(STUDENT2);
        ab.addPerson(STUDENT3);

        model = new ModelManager(ab, new UserPrefs());
        expectedModel = new ModelManager(ab, new UserPrefs());

        //execute list command on non-existent module
        ModuleCode noStudentModuleCode = new ModuleCode("CS9999");
        ListCommand listCommand = new ListCommand(noStudentModuleCode);

        Predicate<Person> modulePredicate = person -> person.getModuleCodes().stream()
                .anyMatch(mc -> mc.equals(noStudentModuleCode));

        expectedModel.updateFilteredPersonList(modulePredicate);

        String expectedMessage = String.format(ListCommand.MESSAGE_NO_STUDENTS_FOUND, noStudentModuleCode);

        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
        assertTrue(model.getFilteredPersonList().isEmpty());

    }
}
