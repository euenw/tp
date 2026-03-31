package cpp.model.contact;

import java.util.List;

import cpp.commons.util.StringUtil;

/**
 * Tests that a {@code Contact}'s {@code Phone} matches any of the keywords
 * given.
 */
public class ContactPhoneContainsKeywordsPredicate implements ContactSearchPredicate {
    private final List<String> keywords;

    public ContactPhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Contact contact) {
        return this.keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(contact.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ContactPhoneContainsKeywordsPredicate)) {
            return false;
        }

        ContactPhoneContainsKeywordsPredicate otherPredicate = (ContactPhoneContainsKeywordsPredicate) other;
        return this.keywords.equals(otherPredicate.keywords);
    }
}
