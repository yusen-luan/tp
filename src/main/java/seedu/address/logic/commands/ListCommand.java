package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.person.Person;

/**
 * Lists all students in TeachMate to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Showing all students (%d total)";

    public static final String MESSAGE_SUCCESS_MODULE = "Showing students in %s (%d found)";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all students or filtered by module.\n"
            + "Format: " + COMMAND_WORD + " [m/MODULE_CODE]\n";

    public static final String MESSAGE_NO_STUDENTS_FOUND =
            "No students enrolled in %s. Use 'list' to see all students.";

    private final Optional<ModuleCode> moduleCode;

    // If moduleCode is empty, list all persons. If moduleCode is present, list persons in that module.
    public ListCommand() {
        this.moduleCode = Optional.empty();
    }

    public ListCommand(ModuleCode moduleCode) {
        this.moduleCode = Optional.of(moduleCode);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (moduleCode.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            int count = model.getFilteredPersonList().size();
            return new CommandResult(String.format(MESSAGE_SUCCESS, count));
        }

        ModuleCode target = moduleCode.get();
        // predicate to filter persons by module code
        Predicate<Person> modulePredicate = person -> person.getModuleCodes().stream()
                .anyMatch(mc -> mc.equals(moduleCode.get()));

        model.updateFilteredPersonList(modulePredicate);

        int count = model.getFilteredPersonList().size();
        if (count == 0) {
            return new CommandResult(String.format(MESSAGE_NO_STUDENTS_FOUND, target));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS_MODULE, target, count));
        }

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListCommand)) {
            return false;
        }

        ListCommand otherCommand = (ListCommand) other;
        return moduleCode.equals(otherCommand.moduleCode);
    }

    @Override
    public int hashCode() {
        return moduleCode.hashCode();
    }

}
