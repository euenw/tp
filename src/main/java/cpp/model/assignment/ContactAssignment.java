package cpp.model.assignment;

import java.time.LocalDateTime;
import java.util.Objects;

import cpp.commons.util.CollectionUtil;
import cpp.model.assignment.exceptions.ContactAssignmentAlreadySubmittedException;
import cpp.model.assignment.exceptions.ContactAssignmentNotSubmittedException;

/**
 * Association between an Assignment and a Contact (by id).
 * Holds per-contact state such as submission and grading.
 */
public class ContactAssignment {

    // Identity fields
    private final String assignmentId;
    private final String contactId;

    // Data fields
    private boolean isSubmitted;
    private LocalDateTime submissionDate;
    private boolean isGraded;
    private LocalDateTime gradingDate;
    private int score;

    /**
     * Creates a contact assignment between the given assignment and contact ids.
     * The contact assignment is initially not submitted and not graded.
     */
    public ContactAssignment(String assignmentId, String contactId) {
        CollectionUtil.requireAllNonNull(assignmentId, contactId);
        this.assignmentId = assignmentId;
        this.contactId = contactId;
        this.isSubmitted = false;
        this.submissionDate = null;
        this.isGraded = false;
        this.gradingDate = null;
        this.score = 0;
    }

    /**
     * Creates a contact assignment with the given details. This constructor is used
     * for loading from storage, where the submission and grading state is already
     * available.
     */
    public ContactAssignment(String assignmentId, String contactId, boolean isSubmitted, LocalDateTime submissionDate,
            boolean isGraded, LocalDateTime gradingDate, int score) {
        // Note: we allow submissionDate and gradingDate to be null, as they may not be
        // set if the assignment is not yet submitted or graded.
        CollectionUtil.requireAllNonNull(assignmentId, contactId, isSubmitted, isGraded, score);
        this.assignmentId = assignmentId;
        this.contactId = contactId;
        this.isSubmitted = isSubmitted;
        this.submissionDate = submissionDate;
        this.isGraded = isGraded;
        this.gradingDate = gradingDate;
        this.score = score;
    }

    public String getAssignmentId() {
        return this.assignmentId;
    }

    public String getContactId() {
        return this.contactId;
    }

    public boolean isSubmitted() {
        return this.isSubmitted;
    }

    public LocalDateTime getSubmissionDate() {
        return this.submissionDate;
    }

    public boolean isGraded() {
        return this.isGraded;
    }

    public LocalDateTime getGradingDate() {
        return this.gradingDate;
    }

    public int getScore() {
        return this.score;
    }

    /**
     * Marks this contact assignment as submitted. Throws an exception if it is
     * already marked as submitted.
     */
    public void markSubmitted(LocalDateTime submissionDate) {
        if (this.isSubmitted) {
            throw new ContactAssignmentAlreadySubmittedException();
        }
        this.isSubmitted = true;
        this.submissionDate = submissionDate;
    }

    /**
     * Marks this contact assignment as unsubmitted. Throws an exception if it is
     * not currently marked as submitted. Unsubmitting also resets the grading state
     * to ungraded and score to 0.
     */
    public void markUnsubmitted() {
        if (!this.isSubmitted) {
            throw new ContactAssignmentNotSubmittedException();
        }
        this.isGraded = false;
        this.gradingDate = null;
        this.score = 0;
        this.isSubmitted = false;
        this.submissionDate = null;
    }

    /**
     * Grade this contact assignment with the given score. Marks as graded.
     *
     * @param score the score to assign to this contact assignment
     */
    public void grade(int score) {
        this.isGraded = true;
        this.score = score;
    }

    /**
     * Remove the grade from this contact assignment. Marks as ungraded.
     */
    public void ungrade() {
        this.isGraded = false;
        this.score = 0;
    }

    /**
     * Returns true if both contact assignments have the same assignment and contact
     * ids.
     *
     * @param other the other contact assignment to compare with
     * @return true if both contact assignments have the same assignment and contact
     *         ids
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ContactAssignment)) {
            return false;
        }
        ContactAssignment o = (ContactAssignment) other;
        return this.assignmentId.equals(o.assignmentId)
                && this.contactId.equals(o.contactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.assignmentId, this.contactId);
    }

    @Override
    public String toString() {
        return "ContactAssignment[assignmentId=" + this.assignmentId
                + ", contactId=" + this.contactId
                + ", submitted=" + this.isSubmitted
                + ", graded=" + this.isGraded
                + ", score=" + this.score + "]";
    }
}
