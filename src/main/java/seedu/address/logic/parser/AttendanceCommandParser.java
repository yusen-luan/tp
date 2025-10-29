package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.attendance.Week;
import seedu.address.model.person.StudentId;

/**
 * Parses input arguments and creates a new AttendanceCommand object
 */
public class AttendanceCommandParser implements Parser<AttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AttendanceCommand
     * and returns an AttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_ID, PREFIX_WEEK);

        // Check if student ID prefix is present
        if (argMultimap.getValue(PREFIX_STUDENT_ID).isPresent()) {
            return parseByStudentId(argMultimap);
        } else {
            return parseByIndex(args, argMultimap);
        }
    }

    /**
     * Parses the command by student ID (or "all").
     */
    private AttendanceCommand parseByStudentId(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_WEEK).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_ID, PREFIX_WEEK);

        String studentIdString = argMultimap.getValue(PREFIX_STUDENT_ID).get();
        String weekAndStatus = argMultimap.getValue(PREFIX_WEEK).get();

        // Extract week and status from the week parameter (format: "w/1 present")
        String[] parts = weekAndStatus.trim().split("\\s+", 2);
        if (parts.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE));
        }

        String weekString = parts[0];
        String statusString = parts[1];

        Week week = ParserUtil.parseWeek(weekString);
        AttendanceStatus status = ParserUtil.parseAttendanceStatus(statusString);

        // Check if this is a "mark all" command
        if ("all".equalsIgnoreCase(studentIdString)) {
            StudentId nullStudentId = null;
            return new AttendanceCommand(nullStudentId, week, status); // null studentId indicates mark all
        } else {
            StudentId studentId = ParserUtil.parseStudentId(studentIdString);
            return new AttendanceCommand(studentId, week, status);
        }
    }

    /**
     * Parses the command by index.
     */
    private AttendanceCommand parseByIndex(String args, ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_WEEK).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_WEEK);

        // Parse the index from preamble
        String preamble = argMultimap.getPreamble().trim();
        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE), pe);
        }

        String weekAndStatus = argMultimap.getValue(PREFIX_WEEK).get();

        // Extract week and status from the week parameter (format: "w/1 present")
        String[] parts = weekAndStatus.trim().split("\\s+", 2);
        if (parts.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE));
        }

        String weekString = parts[0];
        String statusString = parts[1];

        Week week = ParserUtil.parseWeek(weekString);
        AttendanceStatus status = ParserUtil.parseAttendanceStatus(statusString);

        return new AttendanceCommand(index, week, status);
    }
}
