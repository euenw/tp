package cpp.model.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cpp.testutil.Assert;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equals_caseInsensitive_success() {
        Tag lowerCaseTag = new Tag("friend");
        Tag mixedCaseTag = new Tag("FrIeNd");

        Assertions.assertEquals(lowerCaseTag, mixedCaseTag);
    }

    @Test
    public void hashCode_caseInsensitive_success() {
        Tag lowerCaseTag = new Tag("friend");
        Tag mixedCaseTag = new Tag("FrIeNd");

        Assertions.assertEquals(lowerCaseTag.hashCode(), mixedCaseTag.hashCode());
    }

}
