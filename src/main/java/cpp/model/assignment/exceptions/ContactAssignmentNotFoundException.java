package cpp.model.assignment.exceptions;

/**
 * Signals that the operation is unable to find the specified
 * contact-assignment.
 */
public class ContactAssignmentNotFoundException extends RuntimeException {
    public ContactAssignmentNotFoundException() {
        super("This assignment is not allocated to the contact");
    }
}
