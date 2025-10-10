package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all students";

    public static final String MESSAGE_SUCCESS_MODULE = "Listed all students in module: %s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all students or filtered by module.\n"
            + "Format: " + COMMAND_WORD + " [m/MODULE_CODE]\n";

    public static final String MESSAGE_NO_STUDENTS_FOUND = "No students found in this module: %s";

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
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        //Added predicate here for module filtering, add logic for handling two types of listing for v1.3
        Predicate<Person> modulePredicate = person -> person.getModuleCode().equals(moduleCode.get());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
