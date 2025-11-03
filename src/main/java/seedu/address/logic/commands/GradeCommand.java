package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.grade.Grade;
import seedu.address.model.person.Person;

/**
 * Adds grades to an existing student in TeachMate.
 */
public class GradeCommand extends Command {

    public static final String COMMAND_WORD = "grade";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds or updates grades for the student identified "
            + "by the index number used in the displayed student list. "
            + "If a grade for an assignment already exists (case-insensitive), it will be updated.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_GRADE + "ASSIGNMENT_NAME:SCORE...\n"
            + "Note: SCORE must be a number between 0 and 100.\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GRADE + "Midterm:85 "
            + PREFIX_GRADE + "Assignment1:92";

    public static final String MESSAGE_ADD_GRADE_SUCCESS = "✓ Added %s to %s:\n%s";
    public static final String MESSAGE_UPDATE_GRADE_SUCCESS = "✓ Updated %s for %s:\n%s";
    public static final String MESSAGE_MIXED_GRADE_SUCCESS = "✓ Added %s and updated %s for %s:\n%s";
    public static final String MESSAGE_NOT_STUDENT = "The person at the specified index is not a student. "
            + "Grades can only be added to students.";

    private final Index index;
    private final Set<Grade> gradesToAdd;

    /**
     * @param index of the person in the filtered person list to add grades to
     * @param gradesToAdd grades to be added to the person
     */
    public GradeCommand(Index index, Set<Grade> gradesToAdd) {
        requireNonNull(index);
        requireNonNull(gradesToAdd);

        this.index = index;
        this.gradesToAdd = gradesToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // Check if the person is a student (has a student ID)
        if (personToEdit.getStudentId() == null) {
            throw new CommandException(MESSAGE_NOT_STUDENT);
        }

        // Separate new grades into those that add new assignments vs update existing ones
        Set<Grade> toAdd = new HashSet<>();
        Set<Grade> toUpdate = new HashSet<>();

        for (Grade newGrade : gradesToAdd) {
            boolean isUpdate = false;
            for (Grade existingGrade : personToEdit.getGrades()) {
                // Case-insensitive comparison for assignment names
                if (existingGrade.assignmentName.equalsIgnoreCase(newGrade.assignmentName)) {
                    toUpdate.add(newGrade);
                    isUpdate = true;
                    break;
                }
            }
            if (!isUpdate) {
                toAdd.add(newGrade);
            }
        }

        Person gradedPerson = createGradedPerson(personToEdit, gradesToAdd);

        model.setPerson(personToEdit, gradedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // Generate appropriate success message
        String gradesFormatted = Messages.formatGrades(gradesToAdd);
        if (!toAdd.isEmpty() && !toUpdate.isEmpty()) {
            String addCount = Messages.formatCount(toAdd.size(), "grade");
            String updateCount = Messages.formatCount(toUpdate.size(), "grade");
            return new CommandResult(String.format(MESSAGE_MIXED_GRADE_SUCCESS,
                    addCount, updateCount, Messages.formatStudentId(gradedPerson), gradesFormatted));
        } else if (!toUpdate.isEmpty()) {
            String updateCount = Messages.formatCount(toUpdate.size(), "grade");
            return new CommandResult(String.format(MESSAGE_UPDATE_GRADE_SUCCESS,
                    updateCount, Messages.formatStudentId(gradedPerson), gradesFormatted));
        } else {
            String addCount = Messages.formatCount(toAdd.size(), "grade");
            return new CommandResult(String.format(MESSAGE_ADD_GRADE_SUCCESS,
                    addCount, Messages.formatStudentId(gradedPerson), gradesFormatted));
        }
    }

    /**
     * Creates and returns a {@code Person} with the grades added or updated.
     * If a grade for the same assignment (case-insensitive) already exists, it will be replaced.
     */
    private static Person createGradedPerson(Person personToEdit, Set<Grade> gradesToAdd) {
        assert personToEdit != null;

        Set<Grade> updatedGrades = new HashSet<>();

        // Add existing grades, but skip any that will be updated (case-insensitive check)
        for (Grade existingGrade : personToEdit.getGrades()) {
            boolean willBeUpdated = false;
            for (Grade newGrade : gradesToAdd) {
                if (existingGrade.assignmentName.equalsIgnoreCase(newGrade.assignmentName)) {
                    willBeUpdated = true;
                    break;
                }
            }
            if (!willBeUpdated) {
                updatedGrades.add(existingGrade);
            }
        }

        // Add all new grades (including updates)
        updatedGrades.addAll(gradesToAdd);

        // Create a new person with the updated grades
        // Use student constructor if phone/address are null
        if (personToEdit.getPhone() == null || personToEdit.getAddress() == null) {
            return new Person(personToEdit.getName(), personToEdit.getStudentId(), personToEdit.getEmail(),
                    personToEdit.getModuleCodes(), personToEdit.getTags(),
                    personToEdit.getAttendanceRecord(), updatedGrades, personToEdit.getConsultations(),
                    personToEdit.getRemark());
        } else {
            return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getStudentId(),
                    personToEdit.getModuleCodes(), updatedGrades);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GradeCommand)) {
            return false;
        }

        GradeCommand otherGradeCommand = (GradeCommand) other;
        return index.equals(otherGradeCommand.index)
                && gradesToAdd.equals(otherGradeCommand.gradesToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("gradesToAdd", gradesToAdd)
                .toString();
    }
}
