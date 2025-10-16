package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withTags("friends").withStudentId("A0123456X").withModuleCode("CS2103T")
            .withPhone("94351253").withAddress("123, Jurong West Ave 6, #08-111").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com")
            .withTags("owesMoney", "friends").withStudentId("A0234567Y").withModuleCode("CS2103T")
            .withPhone("98765432").withAddress("311, Clementi Ave 2, #02-25").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withEmail("heinz@example.com")
            .withStudentId("A0345678Z").withModuleCode("CS2040")
            .withPhone("95352563").withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withEmail("cornelia@example.com").withTags("friends")
            .withStudentId("A0456789A").withModuleCode("CS3230")
            .withPhone("87652533").withAddress("10th street").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withEmail("werner@example.com")
            .withStudentId("A0567890B").withModuleCode("CS2106")
            .withPhone("9482224").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withEmail("lydia@example.com")
            .withStudentId("A0678901C").withModuleCode("CS1010")
            .withPhone("9482427").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withEmail("anna@example.com")
            .withStudentId("A0789012D").withModuleCode("CS3244")
            .withPhone("9482442").withAddress("4th street").build();

    // With student fields
    public static final Person ZACK_STUDENT = new PersonBuilder().withName("Zack Lee")
            .withEmail("zack@example.com").withStudentId("A0123456X")
            .withModuleCode("CS2103T").withPhone("91234567").withAddress("kent ridge").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withEmail("stefan@example.com")
            .withStudentId("A0890123E").withModuleCode("CS2101")
            .withPhone("8482424").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withEmail("hans@example.com")
            .withStudentId("A0901234F").withModuleCode("CS1101S")
            .withPhone("8482131").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withEmail(VALID_EMAIL_AMY).withTags(VALID_TAG_FRIEND)
            .withStudentId(VALID_STUDENT_ID_AMY).withModuleCode(VALID_MODULE_CODE_AMY)
            .withPhone(VALID_PHONE_AMY).withAddress(VALID_ADDRESS_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withStudentId(VALID_STUDENT_ID_BOB).withModuleCode(VALID_MODULE_CODE_AMY)
            .withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
