package cpp.logic.commands;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cpp.commons.core.index.Index;
import cpp.logic.commands.exceptions.CommandException;
import cpp.model.assignment.Assignment;
import cpp.model.classgroup.ClassGroup;
import cpp.model.contact.Contact;
import cpp.testutil.AssignmentBuilder;
import cpp.testutil.ClassGroupBuilder;
import cpp.testutil.ContactBuilder;

public class CommandUtilTest {
    @Test
    public void checkContactIndices_validIndices_doesNotThrow() {
        List<Contact> lastShownContactList = List.of(
                new ContactBuilder().withName("Alice").build(),
                new ContactBuilder().withName("Bob").build(),
                new ContactBuilder().withName("Charlie").build());

        List<Index> contactIndices = List.of(Index.fromOneBased(1), Index.fromOneBased(2), Index.fromOneBased(3));

        Assertions.assertDoesNotThrow(() -> CommandUtil.checkContactIndices(lastShownContactList, contactIndices));
    }

    @Test
    public void checkContactIndices_invalidIndex_throwsCommandException() {
        List<Contact> lastShownContactList = List.of(
                new ContactBuilder().withName("Alice").build(),
                new ContactBuilder().withName("Bob").build(),
                new ContactBuilder().withName("Charlie").build());

        List<Index> contactIndices = List.of(Index.fromOneBased(1), Index.fromOneBased(4));

        Assertions.assertThrows(CommandException.class,
                () -> CommandUtil.checkContactIndices(lastShownContactList, contactIndices));
    }

    @Test
    public void checkContactIndices_emptyContactList_throwsCommandException() {
        List<Contact> lastShownContactList = List.of();

        List<Index> contactIndices = List.of(Index.fromOneBased(1));

        Assertions.assertThrows(CommandException.class,
                () -> CommandUtil.checkContactIndices(lastShownContactList, contactIndices));
    }

    @Test
    public void checkAssignmentIndex_validIndex_doesNotThrow() {
        List<Assignment> lastShownAssignmentList = List.of(
                new AssignmentBuilder().withName("Assignment 1").build(),
                new AssignmentBuilder().withName("Assignment 2").build(),
                new AssignmentBuilder().withName("Assignment 3").build());

        Index assignmentIndex = Index.fromOneBased(2);

        Assertions.assertDoesNotThrow(() -> CommandUtil.checkAssignmentIndex(lastShownAssignmentList, assignmentIndex));
    }

    @Test
    public void checkAssignmentIndex_invalidIndex_throwsCommandException() {
        List<Assignment> lastShownAssignmentList = List.of(
                new AssignmentBuilder().withName("Assignment 1").build(),
                new AssignmentBuilder().withName("Assignment 2").build(),
                new AssignmentBuilder().withName("Assignment 3").build());

        Index assignmentIndexOutOfBounds = Index.fromOneBased(4);
        Assertions.assertThrows(CommandException.class,
                () -> CommandUtil.checkAssignmentIndex(lastShownAssignmentList, assignmentIndexOutOfBounds));
    }

    @Test
    public void checkAssignmentIndex_emptyAssignmentList_throwsCommandException() {
        List<Assignment> lastShownAssignmentList = List.of();

        Index assignmentIndex = Index.fromOneBased(1);

        Assertions.assertThrows(CommandException.class,
                () -> CommandUtil.checkAssignmentIndex(lastShownAssignmentList, assignmentIndex));
    }

    @Test
    public void checkClassGroupIndex_validIndex_doesNotThrow() {
        List<ClassGroup> lastShownClassGroupList = List.of(
                new ClassGroupBuilder().withName("Class Group 1").build(),
                new ClassGroupBuilder().withName("Class Group 2").build(),
                new ClassGroupBuilder().withName("Class Group 3").build());

        Index classGroupIndex = Index.fromOneBased(2);

        Assertions.assertDoesNotThrow(() -> CommandUtil.checkClassGroupIndex(lastShownClassGroupList, classGroupIndex));
    }

    @Test
    public void checkClassGroupIndex_invalidIndex_throwsCommandException() {
        List<ClassGroup> lastShownClassGroupList = List.of(
                new ClassGroupBuilder().withName("Class Group 1").build(),
                new ClassGroupBuilder().withName("Class Group 2").build(),
                new ClassGroupBuilder().withName("Class Group 3").build());

        Index classGroupIndexOutOfBounds = Index.fromOneBased(4);
        Assertions.assertThrows(CommandException.class,
                () -> CommandUtil.checkClassGroupIndex(lastShownClassGroupList, classGroupIndexOutOfBounds));
    }

    @Test
    public void checkClassGroupIndex_emptyClassGroupList_throwsCommandException() {
        List<ClassGroup> lastShownClassGroupList = List.of();

        Index classGroupIndex = Index.fromOneBased(1);

        Assertions.assertThrows(CommandException.class,
                () -> CommandUtil.checkClassGroupIndex(lastShownClassGroupList, classGroupIndex));
    }
}
