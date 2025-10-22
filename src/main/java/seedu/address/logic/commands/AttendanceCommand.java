package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.attendance.Week;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Marks attendance for a student identified using their student ID.
 */
public class AttendanceCommand extends Command {

    public static final String COMMAND_WORD = "attendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks attendance for a student identified by their student ID.\n"
            + "Parameters: s/STUDENT_ID w/WEEK present|absent\n"
            + "Example: " + COMMAND_WORD + " s/A0123456X w/1 present";

    public static final String MESSAGE_MARK_ATTENDANCE_SUCCESS = "Marked attendance for %1$s: Week %2$s - %3$s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "No student found with ID: %1$s";

    private final StudentId targetStudentId;
    private final Week week;
    private final AttendanceStatus status;

    /**
     * Creates an AttendanceCommand to mark the attendance of the specified student.
     */
    public AttendanceCommand(StudentId targetStudentId, Week week, AttendanceStatus status) {
        requireNonNull(targetStudentId);
        requireNonNull(week);
        requireNonNull(status);
        this.targetStudentId = targetStudentId;
        this.week = week;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person studentToMarkAttendance = model.findPersonByStudentId(targetStudentId);

        if (studentToMarkAttendance == null) {
            throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, targetStudentId));
        }

        // Mark attendance for the student
        AttendanceRecord updatedAttendanceRecord = studentToMarkAttendance.getAttendanceRecord()
                .markAttendance(week, status);

        // Create updated person with new attendance record
        Person updatedStudent = new Person(
                studentToMarkAttendance.getName(),
                studentToMarkAttendance.getStudentId(),
                studentToMarkAttendance.getEmail(),
                studentToMarkAttendance.getModuleCodes(),
                studentToMarkAttendance.getTags(),
                updatedAttendanceRecord
        );

        // Update the student in the model
        model.setPerson(studentToMarkAttendance, updatedStudent);

        return new CommandResult(String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS,
                updatedStudent.getName(), week, status));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AttendanceCommand)) {
            return false;
        }

        AttendanceCommand otherAttendanceCommand = (AttendanceCommand) other;
        return targetStudentId.equals(otherAttendanceCommand.targetStudentId)
                && week.equals(otherAttendanceCommand.week)
                && status.equals(otherAttendanceCommand.status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetStudentId", targetStudentId)
                .add("week", week)
                .add("status", status)
                .toString();
    }
}
