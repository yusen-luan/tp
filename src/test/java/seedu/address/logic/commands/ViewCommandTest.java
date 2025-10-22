package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewCommand.
 */
public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person personToView = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_PERSON);

        // Just check that the command succeeds and contains basic student info
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(person -> person.equals(personToView));

        CommandResult result = viewCommand.execute(model);
        assertTrue(result.getFeedbackToUser().contains("STUDENT DETAILS"));
        assertTrue(result.getFeedbackToUser().contains(personToView.getName().fullName));
        assertTrue(result.getFeedbackToUser().contains("ATTENDANCE RECORD"));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validStudentId_success() throws Exception {
        Person studentToView = new PersonBuilder().withName("Test Student")
                .withPhone("91234567")
                .withEmail("test@example.com")
                .withAddress("test address")
                .withStudentId("A9876543Z")
                .withModuleCode("CS2103T")
                .build();
        model.addPerson(studentToView);

        StudentId studentId = new StudentId("A9876543Z");
        ViewCommand viewCommand = new ViewCommand(studentId);

        // Just check that the command succeeds and contains basic student info
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(person -> person.equals(studentToView));

        CommandResult result = viewCommand.execute(model);
        assertTrue(result.getFeedbackToUser().contains("STUDENT DETAILS"));
        assertTrue(result.getFeedbackToUser().contains(studentToView.getName().fullName));
        assertTrue(result.getFeedbackToUser().contains("ATTENDANCE RECORD"));
    }

    @Test
    public void execute_invalidStudentId_throwsCommandException() {
        StudentId nonExistentStudentId = new StudentId("A9999999Z");
        ViewCommand viewCommand = new ViewCommand(nonExistentStudentId);

        assertCommandFailure(viewCommand, model,
                String.format(ViewCommand.MESSAGE_STUDENT_NOT_FOUND, nonExistentStudentId));
    }

    @Test
    public void equals() {
        ViewCommand viewFirstCommand = new ViewCommand(INDEX_FIRST_PERSON);
        ViewCommand viewSecondCommand = new ViewCommand(INDEX_SECOND_PERSON);
        StudentId studentId = new StudentId("A9876543Z");
        ViewCommand viewStudentIdCommand = new ViewCommand(studentId);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values (index) -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(INDEX_FIRST_PERSON);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // same values (student ID) -> returns true
        ViewCommand viewStudentIdCommandCopy = new ViewCommand(new StudentId("A9876543Z"));
        assertTrue(viewStudentIdCommand.equals(viewStudentIdCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person index -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));

        // different search method (index vs student ID) -> returns false
        assertFalse(viewFirstCommand.equals(viewStudentIdCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ViewCommand viewCommand = new ViewCommand(targetIndex);
        String expected = ViewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, viewCommand.toString());

        StudentId studentId = new StudentId("A9876543Z");
        ViewCommand viewStudentIdCommand = new ViewCommand(studentId);
        String expectedStudentId = ViewCommand.class.getCanonicalName() + "{studentId=" + studentId + "}";
        assertEquals(expectedStudentId, viewStudentIdCommand.toString());
    }
}

