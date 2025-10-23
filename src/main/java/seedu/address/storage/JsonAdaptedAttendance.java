package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.attendance.Week;

/**
 * Jackson-friendly version of {@link Attendance}.
 */
class JsonAdaptedAttendance {

    private final int week;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedAttendance} with the given attendance details.
     */
    @JsonCreator
    public JsonAdaptedAttendance(@JsonProperty("week") int week, @JsonProperty("status") String status) {
        this.week = week;
        this.status = status;
    }

    /**
     * Converts a given {@code Attendance} into this class for Jackson use.
     */
    public JsonAdaptedAttendance(Attendance source) {
        week = source.getWeek().value;
        status = source.getStatus().value;
    }

    /**
     * Converts a given week and status into this class for Jackson use.
     */
    public JsonAdaptedAttendance(Week week, AttendanceStatus status) {
        this.week = week.value;
        this.status = status.value;
    }

    /**
     * Converts this Jackson-friendly adapted attendance object into the model's {@code Attendance} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted attendance.
     */
    public Attendance toModelType() throws IllegalValueException {
        if (!Week.isValidWeek(week)) {
            throw new IllegalValueException(Week.MESSAGE_CONSTRAINTS);
        }
        final Week modelWeek = new Week(week);

        if (status == null) {
            throw new IllegalValueException("Attendance status is missing!");
        }
        try {
            final AttendanceStatus modelStatus = AttendanceStatus.fromString(status);
            return new Attendance(modelWeek, modelStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Invalid attendance status: " + status);
        }
    }
}
