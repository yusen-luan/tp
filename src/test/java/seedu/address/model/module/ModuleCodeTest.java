package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleCodeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleCode(null));
    }

    @Test
    public void constructor_invalidModuleCode_throwsIllegalArgumentException() {
        String invalidModuleCode = "";
        assertThrows(IllegalArgumentException.class, () -> new ModuleCode(invalidModuleCode));
    }

    @Test
    public void isValidModuleCode() {
        assertThrows(NullPointerException.class, () -> ModuleCode.isValidModuleCode(null));
        // Invalid module codes - test the variations!
        assertFalse(ModuleCode.isValidModuleCode(""));
        assertFalse(ModuleCode.isValidModuleCode(" "));
        assertFalse(ModuleCode.isValidModuleCode("C2103T"));
        assertFalse(ModuleCode.isValidModuleCode("CSCI2103"));
        assertFalse(ModuleCode.isValidModuleCode("CS210"));
        assertFalse(ModuleCode.isValidModuleCode("CS21034"));
        assertFalse(ModuleCode.isValidModuleCode("cs2103t"));
        assertFalse(ModuleCode.isValidModuleCode("CS 2103T"));
        // Valid module codes - test the variations!
        assertTrue(ModuleCode.isValidModuleCode("CS2103"));
        assertTrue(ModuleCode.isValidModuleCode("CS2103T"));
        assertTrue(ModuleCode.isValidModuleCode("GEA1000"));
        assertTrue(ModuleCode.isValidModuleCode("GEA1000H"));
        assertTrue(ModuleCode.isValidModuleCode("AA0000"));
        assertTrue(ModuleCode.isValidModuleCode("ZZZ9999Z"));
    }

    @Test
    public void equals() {
        ModuleCode moduleCode = new ModuleCode("CS2103");
        assertTrue(moduleCode.equals(new ModuleCode("CS2103")));
        assertTrue(moduleCode.equals(moduleCode));
        assertFalse(moduleCode.equals(null));
        assertFalse(moduleCode.equals(new Object()));
        assertFalse(moduleCode.equals(5.0f));
        assertFalse(moduleCode.equals(new ModuleCode("CS2103T")));
    }
}
