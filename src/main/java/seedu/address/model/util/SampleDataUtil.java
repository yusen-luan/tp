package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new StudentId("A0123456X"), new Email("alexyeoh@example.com"),
                new ModuleCode("CS2103T"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new StudentId("A0765432B"), new Email("berniceyu@example.com"),
                new ModuleCode("CS2030S"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new StudentId("A0555555C"), new Email("charlotte@example.com"),
                new ModuleCode("CS2100"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new StudentId("A0999999D"), new Email("lidavid@example.com"),
                new ModuleCode("CS1101S"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new StudentId("A0234567E"), new Email("irfan@example.com"),
                new ModuleCode("CS2101"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new StudentId("A0456123F"), new Email("royb@example.com"),
                new ModuleCode("CS2040S"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
