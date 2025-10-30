package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULTATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a student to TeachMate.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to TeachMate. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_STUDENT_ID + "STUDENT_ID "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_MODULE_CODE + "MODULE_CODE "
            + "[" + PREFIX_MODULE_CODE + "MORE_MODULES]... "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "[" + PREFIX_CONSULTATION + "CONSULTATION]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_STUDENT_ID + "A0123456X "
            + PREFIX_EMAIL + "johnd@u.nus.edu "
            + PREFIX_MODULE_CODE + "CS2103T "
            + PREFIX_MODULE_CODE + "CS2101 "
            + PREFIX_TAG + "struggling "
            + PREFIX_CONSULTATION + "22 Oct 2025 14:00\n"
            + "Tag and Consultation are optional attributes.";

    public static final String MESSAGE_SUCCESS = "✓ Added student: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT_ID =
            "Cannot add student: A student with ID %1$s already exists in TeachMate.";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check for duplicate student ID if the person has a student ID
        if (toAdd.getStudentId() != null) {
            var existingPerson = model.getPersonByStudentId(toAdd.getStudentId());
            if (existingPerson.isPresent()) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_STUDENT_ID, toAdd.getStudentId()));
            }
        }

        model.addPerson(toAdd);
        return new CommandResult(Messages.successMessage(String.format(MESSAGE_SUCCESS,
                Messages.formatStudentId(toAdd))));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
