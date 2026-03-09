package cpp.model.assignment.exceptions;

/**
 * Signals that the operation is unable to allocate an assignment to a contact
 * because the contact is already allocated the assignment.
 */
public class ContactAlreadyAllocatedAssignmentException extends RuntimeException {
    public ContactAlreadyAllocatedAssignmentException() {
        super("This assignment is already allocated to the contact");
    }
}
