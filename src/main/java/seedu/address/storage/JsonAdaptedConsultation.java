package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.consultation.Consultation;

public class JsonAdaptedConsultation {

    private String dateTime;

    @JsonCreator
    public JsonAdaptedConsultation(@JsonProperty("dateTIme") String dateTime) {
        this.dateTime = dateTime;
    }

    public JsonAdaptedConsultation(Consultation source) {
        this.dateTime = source.getDateTime().toString();
    }

    public Consultation toModelType() throws ParseException {
        // Able to use LocalDateTime.parse directly as storage should not have to deal with input
        return new Consultation(LocalDateTime.parse(dateTime));
    }
}
