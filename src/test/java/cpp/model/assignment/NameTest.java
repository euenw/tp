package cpp.model.assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cpp.testutil.Assert;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName_validName_returnsTrue() {
        Assertions.assertTrue(Name.isValidName("peter jack")); // alphabets only
        Assertions.assertTrue(Name.isValidName("12345")); // numbers only
        Assertions.assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        Assertions.assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        Assertions.assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void isValidName_invalidName_returnsFalse() {
        Assertions.assertFalse(Name.isValidName("")); // empty string
        Assertions.assertFalse(Name.isValidName(" ")); // spaces only
        Assertions.assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        Assertions.assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
    }

    @Test
    public void isValidName_null_returnsFalse() {
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        Assertions.assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        Assertions.assertTrue(name.equals(name));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        Name name = new Name("Valid Name");

        // different types -> returns false
        Assertions.assertFalse(name.equals(5));

        // null -> returns false
        Assertions.assertFalse(name.equals(null));

        // different name -> returns false
        Assertions.assertFalse(name.equals(new Name("Different Name")));
    }

}
