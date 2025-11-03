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
        assertFalse(ModuleCode.isValidModuleCode("2103T")); // No prefix (starts with digit)
        assertFalse(ModuleCode.isValidModuleCode("C2103T")); // 1-letter prefix (too short)
        assertFalse(ModuleCode.isValidModuleCode("A1000")); // 1-letter prefix (too short)
        assertFalse(ModuleCode.isValidModuleCode("CSCIA2103")); // 5-letter prefix (too long)
        assertFalse(ModuleCode.isValidModuleCode("CS210")); // Too few digits (3)
        assertFalse(ModuleCode.isValidModuleCode("CS21034")); // Too many digits (5)
        assertFalse(ModuleCode.isValidModuleCode("CS210345")); // Too many digits (6)
        assertFalse(ModuleCode.isValidModuleCode("cs2103t")); // Lowercase
        assertFalse(ModuleCode.isValidModuleCode("CS 2103T")); // Space
        // Valid module codes - test the variations!
        // Valid module codes with 2-letter prefixes
        assertTrue(ModuleCode.isValidModuleCode("CS2103"));
        assertTrue(ModuleCode.isValidModuleCode("CS2103T"));
        assertTrue(ModuleCode.isValidModuleCode("GEA1000"));
        assertTrue(ModuleCode.isValidModuleCode("GEA1000H"));
        assertTrue(ModuleCode.isValidModuleCode("AA0000"));
        assertTrue(ModuleCode.isValidModuleCode("ZZZ9999Z"));
        // Valid module codes with 3-letter prefixes
        assertTrue(ModuleCode.isValidModuleCode("ACC1701"));
        assertTrue(ModuleCode.isValidModuleCode("MAE1000"));
        assertTrue(ModuleCode.isValidModuleCode("UTC1114"));
        assertTrue(ModuleCode.isValidModuleCode("ESP1107"));
        // Valid module codes with 4-letter prefixes
        assertTrue(ModuleCode.isValidModuleCode("GESS1000"));
        assertTrue(ModuleCode.isValidModuleCode("ACC1701XA"));
        // Valid module codes with 2-letter suffixes
        assertTrue(ModuleCode.isValidModuleCode("CS2040DE"));
        assertTrue(ModuleCode.isValidModuleCode("LL4008BV"));
        // Valid graduate-level module codes
        assertTrue(ModuleCode.isValidModuleCode("BMA5001"));
        assertTrue(ModuleCode.isValidModuleCode("FIN4112A"));
        assertTrue(ModuleCode.isValidModuleCode("MBA5003"));
        assertTrue(ModuleCode.isValidModuleCode("SPH5001"));
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
