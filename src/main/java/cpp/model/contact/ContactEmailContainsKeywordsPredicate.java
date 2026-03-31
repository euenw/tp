package cpp.model.contact;

import java.util.List;

import cpp.commons.util.StringUtil;

/**
 * Tests that a {@code Contact}'s {@code Email} matches any of the keywords
 * given.
 */
public class ContactEmailContainsKeywordsPredicate implements ContactSearchPredicate {
    private final List<String> keywords;

    public ContactEmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Contact contact) {
        return this.keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(contact.getEmail().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ContactEmailContainsKeywordsPredicate)) {
            return false;
        }

        ContactEmailContainsKeywordsPredicate otherPredicate = (ContactEmailContainsKeywordsPredicate) other;
        return this.keywords.equals(otherPredicate.keywords);
    }
}
