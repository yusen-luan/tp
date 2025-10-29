package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        String expectedMessage = Messages.successMessage(String.format(AddCommand.MESSAGE_SUCCESS,
                Messages.formatStudentId(validPerson)));
        assertCommandSuccess(new AddCommand(validPerson), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateStudentId_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        // Create a new person with the same student ID but different name
        Person personWithDuplicateId = new PersonBuilder()
                .withName("Different Name")
                .withStudentId(personInList.getStudentId().value)
                .withEmail("different@example.com")
                .build();
        String expectedMessage = String.format(AddCommand.MESSAGE_DUPLICATE_STUDENT_ID,
                personInList.getStudentId());
        assertCommandFailure(new AddCommand(personWithDuplicateId), model, expectedMessage);
    }

}
