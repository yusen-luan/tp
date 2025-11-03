package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULTATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_STUDENT_ID,
                        PREFIX_EMAIL, PREFIX_MODULE_CODE, PREFIX_TAG, PREFIX_CONSULTATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_STUDENT_ID,
                PREFIX_EMAIL, PREFIX_MODULE_CODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_STUDENT_ID, PREFIX_EMAIL);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        StudentId studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENT_ID).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Set<ModuleCode> moduleCodeList = ParserUtil.parseModuleCodes(argMultimap.getAllValues(PREFIX_MODULE_CODE));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        List<Consultation> consultationList = new ArrayList<>();
        if (argMultimap.getValue(PREFIX_CONSULTATION).isPresent()) {
            for (String consultationString : argMultimap.getAllValues(PREFIX_CONSULTATION)) {
                consultationList.add(new Consultation(ParserUtil.parseDateTime(consultationString)));
            }
            // --- Detect duplicate consultations before removing ---
            List<Consultation> distinctList = consultationList.stream()
                    .distinct()
                    .collect(Collectors.toList());

            if (distinctList.size() < consultationList.size()) {
                // Identify duplicates for clearer message
                Set<Consultation> seen = new HashSet<>();
                Set<Consultation> duplicates = consultationList.stream()
                        .filter(c -> !seen.add(c))
                        .collect(Collectors.toSet());

                String duplicateTimes = duplicates.stream()
                        .map(Consultation::getDateTime)
                        .map(dt -> dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                        .collect(Collectors.joining(", "));

                throw new ParseException("Duplicate consultation detected: " + duplicateTimes
                        + ". Please remove duplicates and try again.");
            }

            // --- Only reach here if no duplicates found ---
            consultationList = distinctList;
        }

        Person person;
        if (consultationList.isEmpty()) {
            person = new Person(name, studentId, email, moduleCodeList, tagList,
                    new AttendanceRecord(), new HashSet<>());
        } else {
            person = new Person(name, studentId, email, moduleCodeList, tagList,
                    null, new HashSet<>(), consultationList, null);
        }

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
