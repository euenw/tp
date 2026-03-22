package cpp.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import cpp.commons.exceptions.IllegalValueException;
import cpp.logic.parser.ParserUtil;
import cpp.model.AddressBook;
import cpp.model.assignment.ContactAssignment;

/**
 * Jackson-friendly version of {@link ContactAssignment}.
 */
class JsonAdaptedContactAssignment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ContactAssignment's %s field is missing!";
    public static final String INVALID_ASSIGNMENT_ID_MESSAGE = """
            Assignment with id %s does not exist in the address book""";
    public static final String INVALID_CONTACT_ID_MESSAGE = "Contact with id %s does not exist in the address book";
    public static final String INVALID_SCORE_MESSAGE = "Score must be a non-negative integer below 100";
    public static final String GRADED_BUT_NOT_SUBMITTED_MESSAGE = """
            A contact assignment cannot be graded if it has not been submitted""";
    public static final String INVALID_GRADING_DATE_MESSAGE = """
            Grading date %s cannot be before submission date %s""";

    private final String assignmentId;
    private final String contactId;
    private final Boolean isSubmitted;
    private final String submissionDate;
    private final Boolean isGraded;
    private final String gradingDate;
    private final Integer score;

    /**
     * Constructs a {@code JsonAdaptedContactAssignment} with the given details.
     */
    @JsonCreator
    public JsonAdaptedContactAssignment(@JsonProperty("assignmentId") String assignmentId,
            @JsonProperty("contactId") String contactId, @JsonProperty("isSubmitted") Boolean isSubmitted,
            @JsonProperty("submissionDate") String submissionDate, @JsonProperty("isGraded") Boolean isGraded,
            @JsonProperty("gradingDate") String gradingDate, @JsonProperty("score") Integer score) {
        this.assignmentId = assignmentId;
        this.contactId = contactId;
        this.isSubmitted = isSubmitted;
        this.submissionDate = submissionDate;
        this.isGraded = isGraded;
        this.gradingDate = gradingDate;
        this.score = score;
    }

    /**
     * Converts a given {@code ContactAssignment} into this class for Jackson use.
     */
    public JsonAdaptedContactAssignment(ContactAssignment source) {
        this.assignmentId = source.getAssignmentId();
        this.contactId = source.getContactId();
        this.isSubmitted = source.isSubmitted();
        this.submissionDate = source.getSubmissionDate() != null
                ? source.getSubmissionDate().format(ParserUtil.DATETIME_FORMATTER)
                : null;
        this.isGraded = source.isGraded();
        this.gradingDate = source.getGradingDate() != null
                ? source.getGradingDate().format(ParserUtil.DATETIME_FORMATTER)
                : null;
        this.score = source.getScore();
    }

    /**
     * Converts this Jackson-friendly adapted contact assignment object into the
     * model's {@code ContactAssignment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated
     *                               in the adapted contact assignment.
     */
    public ContactAssignment toModelType(AddressBook addressBook) throws IllegalValueException {
        if (this.assignmentId == null) {
            throw new IllegalValueException(String.format(JsonAdaptedContactAssignment.MISSING_FIELD_MESSAGE_FORMAT,
                    "assignmentId"));
        }
        if (!addressBook.hasAssignmentId(this.assignmentId)) {
            throw new IllegalValueException(
                    String.format(JsonAdaptedContactAssignment.INVALID_ASSIGNMENT_ID_MESSAGE, this.assignmentId));
        }
        if (this.contactId == null) {
            throw new IllegalValueException(String.format(JsonAdaptedContactAssignment.MISSING_FIELD_MESSAGE_FORMAT,
                    "contactId"));
        }
        if (!addressBook.hasContactId(this.contactId)) {
            throw new IllegalValueException(
                    String.format(JsonAdaptedContactAssignment.INVALID_CONTACT_ID_MESSAGE, this.contactId));
        }
        if (this.isSubmitted == null) {
            throw new IllegalValueException(String.format(JsonAdaptedContactAssignment.MISSING_FIELD_MESSAGE_FORMAT,
                    "isSubmitted"));
        }

        final LocalDateTime modelSubmissionDate;
        try {
            if (this.submissionDate != null) {
                modelSubmissionDate = LocalDateTime.parse(this.submissionDate, ParserUtil.DATETIME_FORMATTER);
            } else {
                modelSubmissionDate = null;
            }
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(String.format(JsonAdaptedContactAssignment.MISSING_FIELD_MESSAGE_FORMAT,
                    "submissionDate"));
        }
        if (modelSubmissionDate == null && this.isSubmitted) {
            throw new IllegalValueException(String.format(JsonAdaptedContactAssignment.MISSING_FIELD_MESSAGE_FORMAT,
                    "submissionDate"));
        }
        if (this.isGraded == null) {
            throw new IllegalValueException(String.format(JsonAdaptedContactAssignment.MISSING_FIELD_MESSAGE_FORMAT,
                    "isGraded"));
        }

        final LocalDateTime modelGradingDate;
        try {
            if (this.gradingDate != null) {
                modelGradingDate = LocalDateTime.parse(this.gradingDate, ParserUtil.DATETIME_FORMATTER);
            } else {
                modelGradingDate = null;
            }
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(String.format(JsonAdaptedContactAssignment.MISSING_FIELD_MESSAGE_FORMAT,
                    "gradingDate"));
        }
        if (modelGradingDate == null && this.isGraded) {
            throw new IllegalValueException(String.format(JsonAdaptedContactAssignment.MISSING_FIELD_MESSAGE_FORMAT,
                    "gradingDate"));
        }
        if (this.isGraded && !this.isSubmitted) {
            throw new IllegalValueException(JsonAdaptedContactAssignment.GRADED_BUT_NOT_SUBMITTED_MESSAGE);
        }
        if (this.isGraded && this.isSubmitted && modelSubmissionDate.isAfter(modelGradingDate)) {
            throw new IllegalValueException(String.format(
                    JsonAdaptedContactAssignment.INVALID_GRADING_DATE_MESSAGE,
                    modelGradingDate.format(ParserUtil.DATETIME_FORMATTER),
                    modelSubmissionDate.format(ParserUtil.DATETIME_FORMATTER)));
        }
        if (this.score == null) {
            throw new IllegalValueException(String.format(JsonAdaptedContactAssignment.MISSING_FIELD_MESSAGE_FORMAT,
                    "score"));
        }
        if (this.score < 0 || this.score > 100) {
            throw new IllegalValueException(JsonAdaptedContactAssignment.INVALID_SCORE_MESSAGE);
        }

        return new ContactAssignment(this.assignmentId, this.contactId, this.isSubmitted, modelSubmissionDate,
                this.isGraded, modelGradingDate, this.score);
    }

}
