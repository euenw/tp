package cpp.model.assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cpp.testutil.Assert;

public class ContactAssignmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ContactAssignment(null, "c1"));
        Assert.assertThrows(NullPointerException.class, () -> new ContactAssignment("a1", null));
    }

    @Test
    public void constructor_validAssignment_success() {
        ContactAssignment ca = new ContactAssignment("a1", "c1");
        Assertions.assertEquals("a1", ca.getAssignmentId());
        Assertions.assertEquals("c1", ca.getContactId());
        Assertions.assertFalse(ca.isSubmitted());
        Assertions.assertFalse(ca.isGraded());
        Assertions.assertEquals(0, ca.getScore());
    }

    @Test
    public void constructor_validAssignmentWithState_success() {
        ContactAssignment ca = new ContactAssignment("a1", "c1", true, true, 85);
        Assertions.assertEquals("a1", ca.getAssignmentId());
        Assertions.assertEquals("c1", ca.getContactId());
        Assertions.assertTrue(ca.isSubmitted());
        Assertions.assertTrue(ca.isGraded());
        Assertions.assertEquals(85, ca.getScore());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        ContactAssignment ca1 = new ContactAssignment("a1", "c1");
        ContactAssignment ca2 = new ContactAssignment("a1", "c1");
        Assertions.assertEquals(ca1, ca2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        ContactAssignment ca1 = new ContactAssignment("a1", "c1");
        ContactAssignment ca2 = new ContactAssignment("a2", "c1");
        ContactAssignment ca3 = new ContactAssignment("a1", "c2");
        Assertions.assertNotEquals(ca1, ca2);
        Assertions.assertNotEquals(ca1, ca3);
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        ContactAssignment ca = new ContactAssignment("a1", "c1");
        Assertions.assertNotEquals(ca, null);
    }

    @Test
    public void toString_validContactAssignment_returnsString() {
        ContactAssignment ca = new ContactAssignment(
                "a1", "c1", true, true, 90);
        String expected = """
                ContactAssignment[assignmentId=a1, contactId=c1, submitted=true, graded=true, score=90]""";
        Assertions.assertEquals(expected, ca.toString());
    }

    @Test
    public void markSubmitted() {
        ContactAssignment ca = new ContactAssignment("a1", "c1");
        Assertions.assertFalse(ca.isSubmitted());
        ca.markSubmitted();
        Assertions.assertTrue(ca.isSubmitted());
    }

    @Test
    public void markUnsubmitted() {
        ContactAssignment ca = new ContactAssignment("a1", "c1", true, false, 0);
        Assertions.assertTrue(ca.isSubmitted());
        ca.markUnsubmitted();
        Assertions.assertFalse(ca.isSubmitted());
    }

    @Test
    public void grade_and_ungrade() {
        ContactAssignment ca = new ContactAssignment("a1", "c1");
        Assertions.assertFalse(ca.isGraded());
        Assertions.assertEquals(0, ca.getScore());
        ca.grade(85);
        Assertions.assertTrue(ca.isGraded());
        Assertions.assertEquals(85, ca.getScore());
    }

    @Test
    public void ungrade() {
        ContactAssignment ca = new ContactAssignment("a1", "c1", true, true, 85);
        Assertions.assertTrue(ca.isGraded());
        Assertions.assertEquals(85, ca.getScore());
        ca.ungrade();
        Assertions.assertFalse(ca.isGraded());
        Assertions.assertEquals(0, ca.getScore());
    }

    @Test
    public void getters() {
        ContactAssignment ca = new ContactAssignment("a1", "c1", true, true, 75);
        Assertions.assertEquals("a1", ca.getAssignmentId());
        Assertions.assertEquals("c1", ca.getContactId());
        Assertions.assertTrue(ca.isSubmitted());
        Assertions.assertTrue(ca.isGraded());
        Assertions.assertEquals(75, ca.getScore());
    }

}
