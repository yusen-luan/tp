package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    @Override
    public ListCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE);


        // list command called with no arguments, list all students
        if (argMultimap.getValue(PREFIX_MODULE_CODE).isEmpty()) {
            String trimmedArgs = args.trim();

            if (!trimmedArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }


            return new ListCommand();
        }

        String moduleCode = argMultimap.getValue(PREFIX_MODULE_CODE).get().trim();


        if (!ModuleCode.isValidModuleCode(moduleCode)) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        } else {
            return new ListCommand(new ModuleCode(moduleCode));
        }
    }

}
