package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.StudentId;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validStudentId_returnsDeleteCommand() {
        StudentId studentId = new StudentId("A0123456X");
        assertParseSuccess(parser, " s/A0123456X", new DeleteCommand(studentId));
    }

    @Test
    public void parse_invalidStudentId_throwsParseException() {
        // Invalid format - starts with B instead of A
        assertParseFailure(parser, " s/B0123456X", StudentId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyStudentId_throwsParseException() {
        assertParseFailure(parser, " s/", StudentId.MESSAGE_CONSTRAINTS);
    }
}
