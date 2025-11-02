package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.module.ModuleCode;

public class ListCommandParserTest {

    private ListCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new ListCommandParser();
    }

    @Test
    public void parse_noArgs_success() {
        // Implement test for no arguments
        assertParseSuccess(parser, "", new ListCommand());
        assertParseSuccess(parser, "   ", new ListCommand());
    }

    @Test
    public void parse_validModuleCode_success() {
        // Implement test for valid module code
        assertParseSuccess(parser, " m/CS2103T", new ListCommand(new ModuleCode("CS2103T")));
        assertParseSuccess(parser, " m/  CS2103T  ", new ListCommand(new ModuleCode("CS2103T")));
    }

    @Test
    public void parse_invalidModuleCode_failure() {
        // Implement test for invalid module code
        assertParseFailure(parser, " m/CS21", ModuleCode.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " m/1234567", ModuleCode.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " m/CS2103TTT", ModuleCode.MESSAGE_CONSTRAINTS); // 3-letter suffix (too long)
    }


}
