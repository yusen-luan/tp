package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.person.StudentId;

/**
 * Adds or edits a remark for an existing student in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds or edits a remark for the student identified "
            + "by their student ID. Existing remark will be overwritten.\n"
            + "Parameters: "
            + PREFIX_STUDENT_ID + "STUDENT_ID "
            + PREFIX_REMARK + "REMARK\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENT_ID + "A0123456X "
            + PREFIX_REMARK + "Needs help with OOP concepts";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Student: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Student: %1$s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "No student found with student ID: %1$s";

    private final StudentId studentId;
    private final Remark remark;

    /**
     * @param studentId of the student to add remark to
     * @param remark remark to be added to the student
     */
    public RemarkCommand(StudentId studentId, Remark remark) {
        requireNonNull(studentId);
        requireNonNull(remark);

        this.studentId = studentId;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person studentToEdit = model.findPersonByStudentId(studentId);

        if (studentToEdit == null) {
            throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, studentId));
        }

        Person editedStudent = createStudentWithRemark(studentToEdit, remark);

        model.setPerson(studentToEdit, editedStudent);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_ADD_REMARK_SUCCESS, Messages.format(editedStudent)));
    }

    /**
     * Creates and returns a {@code Person} with the remark updated.
     */
    private static Person createStudentWithRemark(Person studentToEdit, Remark remark) {
        assert studentToEdit != null;

        return new Person(
                studentToEdit.getName(),
                studentToEdit.getStudentId(),
                studentToEdit.getEmail(),
                studentToEdit.getModuleCodes(),
                studentToEdit.getTags(),
                studentToEdit.getAttendanceRecord(),
                studentToEdit.getGrades(),
                studentToEdit.getConsultations(),
                remark
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        RemarkCommand otherCommand = (RemarkCommand) other;
        return studentId.equals(otherCommand.studentId)
                && remark.equals(otherCommand.remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", studentId)
                .add("remark", remark)
                .toString();
    }
}
