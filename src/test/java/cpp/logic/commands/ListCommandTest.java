package cpp.logic.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Contains unit tests for ListCommand base class and its polymorphic behavior.
 */
public class ListCommandTest {

    @Test
    public void equals_sameTab_returnsTrue() {
        ListContactCommand command1 = new ListContactCommand();
        ListContactCommand command2 = new ListContactCommand();
        Assertions.assertEquals(command1, command2);
    }

    @Test
    public void equals_differentTab_returnsFalse() {
        ListContactCommand command1 = new ListContactCommand();
        ListAssignmentCommand command2 = new ListAssignmentCommand();
        Assertions.assertNotEquals(command1, command2);
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        ListContactCommand command = new ListContactCommand();
        Assertions.assertEquals(command, command);
    }

    @Test
    public void equals_notListCommand_returnsFalse() {
        ListContactCommand command = new ListContactCommand();
        Assertions.assertNotEquals(command, new Object());
    }

    @Test
    public void equals_null_returnsFalse() {
        ListContactCommand command = new ListContactCommand();
        Assertions.assertNotEquals(command, null);
    }

    @Test
    public void polymorphism_contactAndAssignment_notEqual() {
        ListContactCommand contact = new ListContactCommand();
        ListAssignmentCommand assignment = new ListAssignmentCommand();
        Assertions.assertNotEquals(contact, assignment);
    }

    @Test
    public void polymorphism_contactAndClass_notEqual() {
        ListContactCommand contact = new ListContactCommand();
        ListClassCommand classCmd = new ListClassCommand();
        Assertions.assertNotEquals(contact, classCmd);
    }

    @Test
    public void polymorphism_assignmentAndClass_notEqual() {
        ListAssignmentCommand assignment = new ListAssignmentCommand();
        ListClassCommand classCmd = new ListClassCommand();
        Assertions.assertNotEquals(assignment, classCmd);
    }

    @Test
    public void polymorphism_allDifferent_returnsFalse() {
        ListContactCommand contact = new ListContactCommand();
        ListAssignmentCommand assignment = new ListAssignmentCommand();
        ListClassCommand classCmd = new ListClassCommand();

        Assertions.assertNotEquals(contact, assignment);
        Assertions.assertNotEquals(assignment, classCmd);
        Assertions.assertNotEquals(contact, classCmd);
    }
}
