package cpp.logic.commands;

import java.util.List;

import cpp.commons.core.index.Index;
import cpp.logic.Messages;
import cpp.logic.commands.exceptions.CommandException;
import cpp.model.assignment.Assignment;
import cpp.model.classgroup.ClassGroup;
import cpp.model.contact.Contact;

/**
 * Utility class for Command related operations.
 */
public class CommandUtil {

    /**
     * Checks if the provided contact indices are valid against the last shown
     * contact list.
     */
    public static void checkContactIndices(List<Contact> lastShownContactList, List<Index> contactIndices)
            throws CommandException {

        // If no contact indices are provided, we consider it as valid (e.g. for
        // commands that allow optional contact indices).
        if (contactIndices.isEmpty()) {
            return;
        }

        if (lastShownContactList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX
                    + '\n'
                    + Messages.EMPTY_CONTACT_LIST);
        }

        for (Index idx : contactIndices) {
            if (idx.getZeroBased() >= lastShownContactList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX
                        + '\n'
                        + String.format(Messages.MESSAGE_VALID_INDEX_BOUNDS, lastShownContactList.size()));
            }
        }
    }

    /**
     * Checks if the provided assignment index is valid against the last shown
     * assignment list.
     * Index is assumed non-null, as this should be used by the edit assignment
     * command which requires a non-null index.
     */
    public static void checkAssignmentIndex(List<Assignment> lastShownAssignmentList, Index assignmentIndex)
            throws CommandException {
        assert assignmentIndex != null : "Assignment index should not be null";
        if (lastShownAssignmentList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX
                    + '\n'
                    + Messages.EMPTY_ASSIGNMENT_LIST);
        }

        if (assignmentIndex.getZeroBased() >= lastShownAssignmentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX
                    + '\n'
                    + String.format(Messages.MESSAGE_VALID_INDEX_BOUNDS, lastShownAssignmentList.size()));
        }
    }

    /**
     * Checks if the provided class group index is valid against the last shown
     * class group list.
     * Index is assumed non-null, as this should be used by the edit class group
     * command which requires a non-null index.
     */
    public static void checkClassGroupIndex(List<ClassGroup> lastShownClassGroupList,
            Index classGroupIndex) throws CommandException {
        assert classGroupIndex != null : "Class group index should not be null";
        if (lastShownClassGroupList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLASS_GROUP_DISPLAYED_INDEX
                    + '\n'
                    + Messages.EMPTY_CLASS_GROUP_LIST);
        }

        if (classGroupIndex.getZeroBased() >= lastShownClassGroupList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLASS_GROUP_DISPLAYED_INDEX
                    + '\n'
                    + String.format(Messages.MESSAGE_VALID_INDEX_BOUNDS, lastShownClassGroupList.size()));
        }
    }
}
