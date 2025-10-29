package seedu.address.model.person;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person} has all of the tags given.
 * Matching is case-insensitive and supports substring matching.
 */
public class PersonHasAllTagsPredicate implements Predicate<Person> {
    private final Set<String> tagKeywords;

    /**
     * Constructs a predicate that tests if a person has all the specified tags.
     * Tag matching is case-insensitive and supports substring matching.
     *
     * @param tags The set of tags to match against
     */
    public PersonHasAllTagsPredicate(Set<Tag> tags) {
        // Store lowercase versions of tag names for case-insensitive matching
        this.tagKeywords = tags.stream()
                .map(tag -> tag.tagName.toLowerCase())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean test(Person person) {
        // Check if for each query tag keyword, there exists at least one person tag
        // that contains it as a substring (case-insensitive)
        return tagKeywords.stream().allMatch(keyword ->
            person.getTags().stream()
                .anyMatch(tag -> tag.tagName.toLowerCase().contains(keyword))
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonHasAllTagsPredicate)) {
            return false;
        }

        PersonHasAllTagsPredicate otherPredicate = (PersonHasAllTagsPredicate) other;
        return tagKeywords.equals(otherPredicate.tagKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tagKeywords", tagKeywords).toString();
    }
}

