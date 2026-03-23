package cpp.logic.commands.assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cpp.commons.core.index.Index;
import cpp.logic.Messages;
import cpp.logic.commands.CommandTestUtil;
import cpp.logic.commands.assignment.EditAssignmentCommand.EditAssignmentDescriptor;
import cpp.model.Model;
import cpp.model.ModelManager;
import cpp.model.UserPrefs;
import cpp.model.assignment.Assignment;
import cpp.model.assignment.AssignmentName;
import cpp.testutil.AssignmentBuilder;
import cpp.testutil.TypicalAssignments;
import cpp.testutil.TypicalContacts;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code EditAssignmentCommand}.
 */
public class EditAssignmentCommandTest {

    private Model model = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        Assignment editedAssignment = new AssignmentBuilder()
                .withId(TypicalAssignments.ASSIGNMENT_ONE.getId())
                .withName("Updated Assignment")
                .withDeadline("25-12-2026 23:59")
                .build();

        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptor();
        descriptor.setName(new AssignmentName("Updated Assignment"));
        descriptor.setDeadline(editedAssignment.getDeadline());

        EditAssignmentCommand editCommand = new EditAssignmentCommand(Index.fromOneBased(1), descriptor);

        String expectedMessage = String.format(EditAssignmentCommand.MESSAGE_EDIT_ASSIGNMENT_SUCCESS,
                Messages.format(editedAssignment));

        ModelManager expectedModel = new ModelManager(this.model.getAddressBook(), new UserPrefs());
        expectedModel.setAssignment(TypicalAssignments.ASSIGNMENT_ONE, editedAssignment);

        CommandTestUtil.assertCommandSuccess(editCommand, this.model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(this.model.getFilteredAssignmentList().size() + 1);
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptor();
        descriptor.setName(new AssignmentName("Some Name"));
        EditAssignmentCommand editCommand = new EditAssignmentCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, this.model,
                Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }

    @Test
    public void editAssignmentDescriptor_noFieldEdited_returnsFalse() {
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptor();
        Assertions.assertFalse(descriptor.isAnyFieldEdited());
    }

    @Test
    public void editAssignmentDescriptor_oneFieldEdited_returnsTrue() {
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptor();
        descriptor.setName(new AssignmentName("Test"));
        Assertions.assertTrue(descriptor.isAnyFieldEdited());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptor();
        descriptor.setName(new AssignmentName("Assignment 1"));
        EditAssignmentCommand cmd1 = new EditAssignmentCommand(Index.fromOneBased(1), descriptor);
        EditAssignmentCommand cmd2 = new EditAssignmentCommand(Index.fromOneBased(1), descriptor);

        Assertions.assertTrue(cmd1.equals(cmd2));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptor();
        descriptor.setName(new AssignmentName("Assignment 1"));
        EditAssignmentCommand cmd = new EditAssignmentCommand(Index.fromOneBased(1), descriptor);

        Assertions.assertTrue(cmd.equals(cmd));
    }

    @Test
    public void equals_differentIndex_returnsFalse() {
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptor();
        descriptor.setName(new AssignmentName("Assignment 1"));
        EditAssignmentCommand cmd1 = new EditAssignmentCommand(Index.fromOneBased(1), descriptor);
        EditAssignmentCommand cmd2 = new EditAssignmentCommand(Index.fromOneBased(2), descriptor);

        Assertions.assertFalse(cmd1.equals(cmd2));
    }

    @Test
    public void equals_differentDescriptor_returnsFalse() {
        EditAssignmentDescriptor descriptor1 = new EditAssignmentDescriptor();
        descriptor1.setName(new AssignmentName("Assignment 1"));
        EditAssignmentDescriptor descriptor2 = new EditAssignmentDescriptor();
        descriptor2.setName(new AssignmentName("Assignment 2"));

        EditAssignmentCommand cmd1 = new EditAssignmentCommand(Index.fromOneBased(1), descriptor1);
        EditAssignmentCommand cmd2 = new EditAssignmentCommand(Index.fromOneBased(1), descriptor2);

        Assertions.assertFalse(cmd1.equals(cmd2));
    }

    @Test
    public void equals_null_returnsFalse() {
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptor();
        descriptor.setName(new AssignmentName("Assignment 1"));
        EditAssignmentCommand cmd = new EditAssignmentCommand(Index.fromOneBased(1), descriptor);

        Assertions.assertFalse(cmd.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        EditAssignmentDescriptor descriptor = new EditAssignmentDescriptor();
        descriptor.setName(new AssignmentName("Assignment 1"));
        EditAssignmentCommand cmd = new EditAssignmentCommand(Index.fromOneBased(1), descriptor);

        Assertions.assertFalse(cmd.equals(42));
    }
}
