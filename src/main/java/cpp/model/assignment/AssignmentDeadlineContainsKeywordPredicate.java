package cpp.model.assignment;

import java.time.LocalDateTime;

/**
 * Tests that an {@code Assignment}'s {@code Deadline} matches the given
 * {@code LocalDateTime} exactly.
 */
public class AssignmentDeadlineContainsKeywordPredicate implements AssignmentSearchPredicate {

    private final LocalDateTime datetimeStart;
    private final LocalDateTime datetimeEnd;

    /**
     * Creates an AssignmentDeadlineContainsKeywordPredicate with the given
     * datetime range. The predicate tests if an assignment's deadline falls within
     * the specified range, inclusive of the start and end datetimes.
     *
     * @param datetimeStart the start datetime for the range
     * @param datetimeEnd   the end datetime for the range
     */
    public AssignmentDeadlineContainsKeywordPredicate(LocalDateTime datetimeStart, LocalDateTime datetimeEnd) {
        this.datetimeStart = datetimeStart;
        this.datetimeEnd = datetimeEnd;
    }

    @Override
    public boolean test(Assignment assignment) {
        LocalDateTime deadline = assignment.getDeadline();

        return !deadline.isBefore(this.datetimeStart) && !deadline.isAfter(this.datetimeEnd);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AssignmentDeadlineContainsKeywordPredicate)) {
            return false;
        }

        AssignmentDeadlineContainsKeywordPredicate otherPredicate = (AssignmentDeadlineContainsKeywordPredicate) other;
        return this.datetimeStart.equals(otherPredicate.datetimeStart)
                && this.datetimeEnd.equals(otherPredicate.datetimeEnd);
    }

    @Override
    public String toString() {
        return new cpp.commons.util.ToStringBuilder(this)
                .add("datetimeStart", this.datetimeStart)
                .add("datetimeEnd", this.datetimeEnd)
                .toString();
    }
}
