package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Deletes a student identified using the displayed index from TeachMate.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the index number or student ID.\n"
            + "Parameters: INDEX (must be a positive integer) or " + PREFIX_STUDENT_ID + "STUDENT_ID\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " " + PREFIX_STUDENT_ID + "A0123456X";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "✓ Deleted student: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND =
            "No student found with student ID %1$s. Use 'list' to see all students.";

    private final Index targetIndex;
    private final StudentId targetStudentId;

    /**
     * Creates a DeleteCommand to delete the person at the specified {@code Index}.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetStudentId = null;
    }

    /**
     * Creates a DeleteCommand to delete the person with the specified {@code StudentId}.
     */
    public DeleteCommand(StudentId targetStudentId) {
        this.targetIndex = null;
        this.targetStudentId = targetStudentId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToDelete;

        if (targetStudentId != null) {
            // Delete by student ID
            Optional<Person> personOptional = model.getPersonByStudentId(targetStudentId);
            if (personOptional.isEmpty()) {
                throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, targetStudentId));
            }
            personToDelete = personOptional.get();
        } else {
            // Delete by index
            List<Person> lastShownList = model.getFilteredPersonList();
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToDelete = lastShownList.get(targetIndex.getZeroBased());
        }

        model.deletePerson(personToDelete);
        return new CommandResult(Messages.successMessage(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.formatStudentId(personToDelete))));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;

        // Check if both use index-based deletion
        if (targetIndex != null && otherDeleteCommand.targetIndex != null) {
            return targetIndex.equals(otherDeleteCommand.targetIndex);
        }

        // Check if both use student ID-based deletion
        if (targetStudentId != null && otherDeleteCommand.targetStudentId != null) {
            return targetStudentId.equals(otherDeleteCommand.targetStudentId);
        }

        // One uses index, the other uses student ID
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("targetStudentId", targetStudentId)
                .toString();
    }
}
