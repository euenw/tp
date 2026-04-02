package cpp.model.assignment;

import java.time.LocalDateTime;

import cpp.logic.parser.ParserUtil;

/**
 * Tests that an {@code Assignment}'s {@code Deadline} matches the given
 * keyword.
 * Supports matching by date formats: dd-MM-yyyy or dd-MM-yyyy HH:mm
 */
public class AssignmentDeadlineContainsKeywordPredicate implements AssignmentSearchPredicate {

    private final String keyword;

    public AssignmentDeadlineContainsKeywordPredicate(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean test(Assignment assignment) {
        LocalDateTime deadline = assignment.getDeadline();

        String fullDateTimeStr = deadline.format(ParserUtil.DATETIME_FORMATTER);
        if (fullDateTimeStr.equalsIgnoreCase(this.keyword)) {
            return true;
        }

        String dateOnlyStr = deadline.format(ParserUtil.DATE_FORMATTER);
        if (dateOnlyStr.equalsIgnoreCase(this.keyword)) {
            return true;
        }

        return false;
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
        return this.keyword.equals(otherPredicate.keyword);
    }

    @Override
    public String toString() {
        return new cpp.commons.util.ToStringBuilder(this)
                .add("keyword", this.keyword)
                .toString();
    }
}
