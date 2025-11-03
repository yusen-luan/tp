package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.attendance.Week;
import seedu.address.model.grade.Grade;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    private static final List<DateTimeFormatter> DATE_FORMATS = List.of(
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm"), //22/10/2025 15:30
            DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm"), //22-10-2025 15:30
            DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"), //2025-10-22 15:30
            DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm") //2025/10/22 15:30
    );


    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String studentId} into a {@code StudentId}.
     * Leading and trailing whitespaces will be trimmed.
     * Student ID is normalized to uppercase to allow case-insensitive input.
     *
     * @throws ParseException if the given {@code studentId} is invalid.
     */
    public static StudentId parseStudentId(String studentId) throws ParseException {
        requireNonNull(studentId);
        String trimmedStudentId = studentId.trim().toUpperCase();
        if (!StudentId.isValidStudentId(trimmedStudentId)) {
            throw new ParseException(StudentId.MESSAGE_CONSTRAINTS);
        }
        return new StudentId(trimmedStudentId);
    }

    /**
     * Parses a {@code String moduleCode} into a {@code ModuleCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code moduleCode} is invalid.
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim();
        if (!ModuleCode.isValidModuleCode(trimmedModuleCode)) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        return new ModuleCode(trimmedModuleCode);
    }

    /**
     * Parses {@code Collection<String> moduleCodes} into a {@code Set<ModuleCode>}.
     */
    public static Set<ModuleCode> parseModuleCodes(Collection<String> moduleCodes) throws ParseException {
        requireNonNull(moduleCodes);
        final Set<ModuleCode> moduleCodeSet = new HashSet<>();
        for (String moduleCodeName : moduleCodes) {
            moduleCodeSet.add(parseModuleCode(moduleCodeName));
        }
        return moduleCodeSet;
    }

    /**
     * Parses a {@code String remark} into a {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemark(trimmedRemark)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }
        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }


    /**
     * Parses a {@code String dateTimeStr} into a {@code LocalDateTime}.
     * @param dateTimeStr
     * @return
     * @throws ParseException
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) throws ParseException {
        LocalDateTime dateTime = null;
        DateTimeFormatter matchedFormatter = null;

        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                dateTime = LocalDateTime.parse(dateTimeStr, formatter);
                matchedFormatter = formatter;
                break;
            } catch (DateTimeParseException ignored) {
                //try next format
            }
        }

        if (matchedFormatter == null) {
            throw new ParseException("Invalid datetime format. Please use one of the following supported formats:\n"
                    + "  • dd/MM/yyyy HH:mm  (e.g. 22/10/2025 15:30)\n"
                    + "  • dd-MM-yyyy HH:mm  (e.g. 22-10-2025 15:30)\n"
                    + "  • yyyy-MM-dd HH:mm  (e.g. 2025-10-22 15:30)\n"
                    + "  • yyyy/MM/dd HH:mm  (e.g. 2025/10/22 15:30)");
        }

        try {
            matchedFormatter.withResolverStyle(ResolverStyle.STRICT).parse(dateTimeStr);
        } catch (DateTimeParseException e) {
            throw new ParseException(
                    "Invalid date value. Please ensure the date exists (e.g. 29 Feb only in leap years).");
        }

        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new ParseException("Consultation time cannot be before the current date and time.");
        }

        return dateTime;
    }

    /**
     * Parses a {@code String week} into a {@code Week}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code week} is invalid.
     */
    public static Week parseWeek(String week) throws ParseException {
        requireNonNull(week);
        String trimmedWeek = week.trim();
        try {
            int weekNumber = Integer.parseInt(trimmedWeek);
            return new Week(weekNumber);
        } catch (NumberFormatException e) {
            throw new ParseException(Week.MESSAGE_CONSTRAINTS);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Week.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String status} into an {@code AttendanceStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static AttendanceStatus parseAttendanceStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        try {
            return AttendanceStatus.fromString(trimmedStatus);
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid attendance status. Use 'present', 'absent', or 'unmark'.");
        }
    }

    /**
     * Parses a {@code String grade} into a {@code Grade}.
     * Grade format should be "ASSIGNMENT_NAME:SCORE"
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code grade} is invalid.
     */
    public static Grade parseGrade(String grade) throws ParseException {
        requireNonNull(grade);
        String trimmedGrade = grade.trim();

        // Split by colon to get assignment name and score
        String[] parts = trimmedGrade.split(":", 2);
        if (parts.length != 2) {
            throw new ParseException("Grade format should be ASSIGNMENT_NAME:SCORE");
        }

        String assignmentName = parts[0].trim();
        String score = parts[1].trim();

        if (!Grade.isValidAssignmentName(assignmentName)) {
            throw new ParseException("Assignment name should not be blank");
        }

        if (!Grade.isValidGrade(score)) {
            throw new ParseException(Grade.MESSAGE_CONSTRAINTS);
        }

        return new Grade(assignmentName, score);
    }

    /**
     * Parses {@code Collection<String> grades} into a {@code Set<Grade>}.
     */
    public static Set<Grade> parseGrades(Collection<String> grades) throws ParseException {
        requireNonNull(grades);
        final Set<Grade> gradeSet = new HashSet<>();
        for (String gradeString : grades) {
            gradeSet.add(parseGrade(gradeString));
        }
        return gradeSet;
    }
}
