package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        String expectedMessage = Messages.successMessage(String.format(AddCommand.MESSAGE_SUCCESS,
                Messages.formatStudentId(validPerson)));
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicateNameDifferentStudentId_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person person1 = new PersonBuilder().withName("Alice").withStudentId("A0123456X").build();
        Person person2 = new PersonBuilder().withName("Alice").withStudentId("A9999999Z").build();

        CommandResult commandResult1 = new AddCommand(person1).execute(modelStub);
        CommandResult commandResult2 = new AddCommand(person2).execute(modelStub);

        String expectedMessage1 = Messages.successMessage(String.format(AddCommand.MESSAGE_SUCCESS,
                Messages.formatStudentId(person1)));
        String expectedMessage2 = Messages.successMessage(String.format(AddCommand.MESSAGE_SUCCESS,
                Messages.formatStudentId(person2)));
        assertEquals(expectedMessage1, commandResult1.getFeedbackToUser());
        assertEquals(expectedMessage2, commandResult2.getFeedbackToUser());
        assertEquals(Arrays.asList(person1, person2), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicateStudentId_throwsCommandException() {
        Person validPerson = new PersonBuilder().withStudentId("A0123456X").build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithStudentId(validPerson.getStudentId());

        String expectedMessage = String.format(AddCommand.MESSAGE_DUPLICATE_STUDENT_ID,
                validPerson.getStudentId());
        assertThrows(CommandException.class, expectedMessage, () ->
                addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Person> getPersonByStudentId(StudentId studentId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person findPersonByStudentId(seedu.address.model.person.StudentId studentId) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a person with a specific student ID.
     */
    private class ModelStubWithStudentId extends ModelStub {
        private final StudentId studentId;

        ModelStubWithStudentId(StudentId studentId) {
            requireNonNull(studentId);
            this.studentId = studentId;
        }

        @Override
        public Optional<Person> getPersonByStudentId(StudentId studentId) {
            requireNonNull(studentId);
            if (this.studentId.equals(studentId)) {
                // Return a dummy person with this student ID
                return Optional.of(new PersonBuilder()
                        .withName("Dummy Person")
                        .withEmail("dummy@example.com")
                        .withStudentId(studentId.value)
                        .build());
            }
            return Optional.empty();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            if (person.getStudentId() != null) {
                // For students, check by student ID
                return personsAdded.stream()
                        .anyMatch(p -> p.getStudentId() != null
                                && p.getStudentId().equals(person.getStudentId()));
            } else {
                // For non-students, check by name
                return personsAdded.stream().anyMatch(person::isSamePerson);
            }
        }

        @Override
        public Optional<Person> getPersonByStudentId(StudentId studentId) {
            requireNonNull(studentId);
            return personsAdded.stream()
                    .filter(person -> person.getStudentId() != null
                            && person.getStudentId().equals(studentId))
                    .findFirst();
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
