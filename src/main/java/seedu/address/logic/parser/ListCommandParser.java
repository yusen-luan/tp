package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import com.fasterxml.jackson.annotation.JsonCreator;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;

public class ListCommandParser implements Parser<ListCommand> {

    @Override
    public ListCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE);


        // list command called with no arguments, list all students
        if (argMultimap.getValue(PREFIX_MODULE_CODE).isEmpty()) {
            return new ListCommand();
        }

        String moduleCode = argMultimap.getValue(PREFIX_MODULE_CODE).get().trim().toUpperCase();

        System.out.println("Checking validity for: " + moduleCode);
        System.out.println("Is valid? " + ModuleCode.isValidModuleCode(moduleCode));


        if (!ModuleCode.isValidModuleCode(moduleCode)) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        } else {
            return new ListCommand(new ModuleCode(moduleCode));
        }
    }

}
