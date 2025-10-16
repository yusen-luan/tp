package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Views detailed information of a student identified using their displayed index or student ID.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the details of the student identified by the index number used in the displayed student list "
            + "or by their student ID.\n"
            + "Parameters: INDEX (must be a positive integer) or s/STUDENT_ID\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " s/A0123456X";

    public static final String MESSAGE_VIEW_STUDENT_SUCCESS = "Viewing student: %1$s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "No student found with ID: %1$s";

    private final Index targetIndex;
    private final StudentId studentId;

    /**
     * Creates a ViewCommand to view the student at the specified {@code Index}.
     */
    public ViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.studentId = null;
    }

    /**
     * Creates a ViewCommand to view the student with the specified {@code StudentId}.
     */
    public ViewCommand(StudentId studentId) {
        this.targetIndex = null;
        this.studentId = studentId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToView;

        if (targetIndex != null) {
            // View by index
            List<Person> lastShownList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            personToView = lastShownList.get(targetIndex.getZeroBased());
        } else {
            // View by student ID
            personToView = model.findPersonByStudentId(studentId);

            if (personToView == null) {
                throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, studentId));
            }
        }

        return new CommandResult(String.format(MESSAGE_VIEW_STUDENT_SUCCESS, Messages.format(personToView)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;

        // Both use index
        if (targetIndex != null && otherViewCommand.targetIndex != null) {
            return targetIndex.equals(otherViewCommand.targetIndex);
        }

        // Both use student ID
        if (studentId != null && otherViewCommand.studentId != null) {
            return studentId.equals(otherViewCommand.studentId);
        }

        return false;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        if (targetIndex != null) {
            builder.add("targetIndex", targetIndex);
        }
        if (studentId != null) {
            builder.add("studentId", studentId);
        }
        return builder.toString();
    }
}

