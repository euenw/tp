package cpp.model.assignment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Tests that an {@code Assignment}'s {@code Deadline} matches the given
 * keyword.
 * Supports matching by date formats: dd-MM-yyyy or dd-MM-yyyy HH:mm
 */
public class AssignmentDeadlineContainsKeywordPredicate implements AssignmentSearchPredicate {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final String keyword;

    public AssignmentDeadlineContainsKeywordPredicate(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean test(Assignment assignment) {
        LocalDateTime deadline = assignment.getDeadline();

        String fullDateTimeStr = deadline.format(AssignmentDeadlineContainsKeywordPredicate.DATETIME_FORMATTER);
        if (fullDateTimeStr.toLowerCase().contains(this.keyword)) {
            return true;
        }

        String dateOnlyStr = deadline.format(AssignmentDeadlineContainsKeywordPredicate.DATE_FORMATTER);
        if (dateOnlyStr.toLowerCase().contains(this.keyword)) {
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
}
