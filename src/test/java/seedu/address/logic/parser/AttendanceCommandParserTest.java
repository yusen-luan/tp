package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AttendanceCommand;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.attendance.Week;
import seedu.address.model.person.StudentId;

/**
 * Contains unit tests for {@link AttendanceCommandParser}.
 */
public class AttendanceCommandParserTest {

    private static final String VALID_STUDENT_ID = "A0123456X";
    private static final String VALID_WEEK = "1";
    private static final String VALID_STATUS_ABSENT = "absent";
    private static final String VALID_STATUS_PRESENT = "present";

    private final AttendanceCommandParser parser = new AttendanceCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = " " + CliSyntax.PREFIX_STUDENT_ID + VALID_STUDENT_ID + " "
                + CliSyntax.PREFIX_WEEK + VALID_WEEK + " " + VALID_STATUS_PRESENT;

        AttendanceCommand expectedCommand = new AttendanceCommand(
                new StudentId(VALID_STUDENT_ID),
                new Week(Integer.parseInt(VALID_WEEK)),
                AttendanceStatus.PRESENT);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseAllFieldsPresentAbsentSuccess() {
        String userInput = " " + CliSyntax.PREFIX_STUDENT_ID + VALID_STUDENT_ID + " "
                + CliSyntax.PREFIX_WEEK + VALID_WEEK + "    " + VALID_STATUS_ABSENT;

        AttendanceCommand expectedCommand = new AttendanceCommand(
                new StudentId(VALID_STUDENT_ID),
                new Week(Integer.parseInt(VALID_WEEK)),
                AttendanceStatus.ABSENT);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingStatus_failure() {
        String userInput = " " + CliSyntax.PREFIX_STUDENT_ID + VALID_STUDENT_ID + " "
                + CliSyntax.PREFIX_WEEK + VALID_WEEK;

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStatus_failure() {
        String userInput = " " + CliSyntax.PREFIX_STUDENT_ID + VALID_STUDENT_ID + " "
                + CliSyntax.PREFIX_WEEK + VALID_WEEK + " maybe";

        assertParseFailure(parser, userInput,
                "Invalid attendance status. Use 'present' or 'absent'.");
    }

    @Test
    public void parse_indexFormat_success() {
        String userInput = " 1 " + CliSyntax.PREFIX_WEEK + VALID_WEEK + " " + VALID_STATUS_PRESENT;

        AttendanceCommand expectedCommand = new AttendanceCommand(
                INDEX_FIRST_PERSON,
                new Week(Integer.parseInt(VALID_WEEK)),
                AttendanceStatus.PRESENT);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_indexFormatAbsent_success() {
        String userInput = " 1 " + CliSyntax.PREFIX_WEEK + VALID_WEEK + " " + VALID_STATUS_ABSENT;

        AttendanceCommand expectedCommand = new AttendanceCommand(
                INDEX_FIRST_PERSON,
                new Week(Integer.parseInt(VALID_WEEK)),
                AttendanceStatus.ABSENT);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String userInput = " 0 " + CliSyntax.PREFIX_WEEK + VALID_WEEK + " " + VALID_STATUS_PRESENT;

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_indexMissingStatus_failure() {
        String userInput = " 1 " + CliSyntax.PREFIX_WEEK + VALID_WEEK;

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_markAllFormat_success() {
        String userInput = " " + CliSyntax.PREFIX_STUDENT_ID + "all "
                + CliSyntax.PREFIX_WEEK + VALID_WEEK + " " + VALID_STATUS_PRESENT;

        StudentId nullStudentId = null;
        AttendanceCommand expectedCommand = new AttendanceCommand(
                nullStudentId,
                new Week(Integer.parseInt(VALID_WEEK)),
                AttendanceStatus.PRESENT);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
