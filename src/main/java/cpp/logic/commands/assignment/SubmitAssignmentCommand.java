package cpp.logic.commands.assignment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import cpp.commons.core.index.Index;
import cpp.commons.util.ToStringBuilder;
import cpp.logic.Messages;
import cpp.logic.commands.Command;
import cpp.logic.commands.CommandResult;
import cpp.logic.commands.CommandUtil;
import cpp.logic.commands.exceptions.CommandException;
import cpp.logic.parser.CliSyntax;
import cpp.model.Model;
import cpp.model.assignment.Assignment;
import cpp.model.assignment.AssignmentName;
import cpp.model.assignment.exceptions.ContactAssignmentAlreadySubmittedException;
import cpp.model.assignment.exceptions.ContactAssignmentNotFoundException;
import cpp.model.classgroup.ClassGroup;
import cpp.model.classgroup.ClassGroupName;
import cpp.model.contact.Contact;
import cpp.model.util.AssignmentUtil;
import cpp.model.util.ClassGroupUtil;

/**
 * Marks an assignment as submitted by contact(s) or class group. The assignment
 * must have been allocated to the contact(s) before it can be submitted.
 */
public class SubmitAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "submitass";

    public static final String MESSAGE_USAGE = SubmitAssignmentCommand.COMMAND_WORD
            + ": Marks an assignment as submitted by contact(s) or class group. "
            + "Parameters: "
            + CliSyntax.PREFIX_ASSIGNMENT + "ASSIGNMENT NAME "
            + "[" + CliSyntax.PREFIX_CLASS + "CLASS NAME] "
            + "[" + CliSyntax.PREFIX_CONTACT + "CONTACT INDICES...]\n"
            + "At least one of " + CliSyntax.PREFIX_CLASS + " or " + CliSyntax.PREFIX_CONTACT + " must be provided.\n"
            + "Example: " + SubmitAssignmentCommand.COMMAND_WORD + " "
            + CliSyntax.PREFIX_ASSIGNMENT + "Assignment 1 "
            + CliSyntax.PREFIX_CLASS + "CS2103T10 "
            + CliSyntax.PREFIX_CONTACT + "1 2 3";

    public static final String MESSAGE_SUCCESS = """
            Marked assignment: %1$s as submitted by %2$s contacts.
            Contacts marked submitted: %3$s
            Contacts not marked submitted (already submitted or not allocated assignment): %4$s""";
    public static final String MESSAGE_SUBMISSION_FAILED = "No contacts were marked as submitted for the assignment";

    private final AssignmentName assignmentName;
    private final List<Index> contactIndices;
    private final Set<Contact> contactsToMark;
    private final ClassGroupName classGroupName;
    private int markedCount = 0;
    private int notMarkedCount = 0;
    private StringBuilder markedContacts;
    private StringBuilder notMarkedContacts;

    /**
     * Creates a SubmitAssignmentCommand to mark the specified assignment as
     * submitted by the specified contacts.
     */
    public SubmitAssignmentCommand(AssignmentName assignmentName, List<Index> contactIndices) {
        Objects.requireNonNull(assignmentName);
        Objects.requireNonNull(contactIndices);
        this.assignmentName = assignmentName;
        this.contactIndices = new ArrayList<>(contactIndices);
        this.classGroupName = null;
        this.contactsToMark = new HashSet<>();
        this.markedContacts = new StringBuilder();
        this.notMarkedContacts = new StringBuilder();
    }

    /**
     * Creates a SubmitAssignmentCommand to mark the specified assignment as
     * submitted by the specified contacts or class group.
     */
    public SubmitAssignmentCommand(AssignmentName assignmentName, List<Index> contactIndices,
            ClassGroupName classGroupName) {
        this.assignmentName = assignmentName;
        this.contactIndices = new ArrayList<>(contactIndices);
        this.classGroupName = classGroupName;
        this.contactsToMark = new HashSet<>();
        this.markedContacts = new StringBuilder();
        this.notMarkedContacts = new StringBuilder();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);

        List<Assignment> assignmentList = model.getAddressBook().getAssignmentList();

        Assignment assignmentToUnallocate = AssignmentUtil.findAssignment(assignmentList, this.assignmentName);

        if (assignmentToUnallocate == null) {
            throw new CommandException(Messages.MESSAGE_ASSIGNMENT_NOT_FOUND);
        }

        List<Contact> lastShownContactList = model.getFilteredContactList();

        CommandUtil.checkContactIndices(lastShownContactList, this.contactIndices);

        ClassGroup classGroupToUnallocate = ClassGroupUtil.findClassGroup(model.getAddressBook().getClassGroupList(),
                this.classGroupName);
        if (this.classGroupName != null && classGroupToUnallocate == null) {
            throw new CommandException(Messages.MESSAGE_CLASS_GROUP_NOT_FOUND);
        }

        this.markSubmittedByContactIndices(model, assignmentToUnallocate, lastShownContactList);
        if (classGroupToUnallocate != null) {
            this.markSubmittedByClassGroup(model, assignmentToUnallocate, classGroupToUnallocate);
        }

        if (this.markedCount == 0) {
            throw new CommandException(SubmitAssignmentCommand.MESSAGE_SUBMISSION_FAILED);
        }

        if (this.notMarkedCount == 0) {
            this.notMarkedContacts.append("None");
        }

        return new CommandResult(String.format(SubmitAssignmentCommand.MESSAGE_SUCCESS,
                Messages.format(assignmentToUnallocate), this.markedCount, this.markedContacts.toString(),
                this.notMarkedContacts.toString()));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SubmitAssignmentCommand)) {
            return false;
        }

        SubmitAssignmentCommand otherCommand = (SubmitAssignmentCommand) other;
        return this.assignmentName.equals(otherCommand.assignmentName)
                && this.contactIndices.equals(otherCommand.contactIndices)
                && Objects.equals(this.classGroupName, otherCommand.classGroupName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("assignmentName", this.assignmentName)
                .add("contactIndices", this.contactIndices)
                .add("classGroupName", this.classGroupName)
                .toString();
    }

    private void markSubmittedByContactIndices(Model model, Assignment assignmentToUnallocate,
            List<Contact> lastShownContactList) {

        for (Index idx : this.contactIndices) {
            Contact contact = lastShownContactList.get(idx.getZeroBased());

            this.markSubmittedByContact(model, assignmentToUnallocate, contact);
        }
    }

    private void markSubmittedByClassGroup(Model model, Assignment assignmentToUnallocate,
            ClassGroup classGroupToUnallocate) {
        List<Contact> contactList = model.getAddressBook().getContactList();

        for (String contactId : classGroupToUnallocate.getContactIdSet()) {
            Contact contact = contactList.stream()
                    .filter(c -> c.getId().equals(contactId))
                    .findAny()
                    .orElse(null);

            if (contact != null) {
                this.markSubmittedByContact(model, assignmentToUnallocate, contact);
            }
        }
    }

    private void markSubmittedByContact(Model model, Assignment assignment, Contact contact) {
        if (this.contactsToMark.contains(contact)) {
            // Skip contacts that have already been marked as submitted through
            // contact indices in the same command
            return;
        }

        try {
            model.markSubmitted(assignment, contact);
            this.markedCount++;
            this.buildSuccessfulMarkString(contact.getName().fullName);
            this.contactsToMark.add(contact);

        } catch (ContactAssignmentNotFoundException e) {
            // Skip contacts that don't have the assignment allocated.
            this.notMarkedCount++;
            this.buildUnsuccessfulMarkString(contact.getName().fullName);
        } catch (ContactAssignmentAlreadySubmittedException e) {
            // Skip contacts that have already been marked as submitted.
            this.notMarkedCount++;
            this.buildUnsuccessfulMarkString(contact.getName().fullName);
        }
    }

    private void buildSuccessfulMarkString(String contactName) {
        if (this.markedContacts.length() > 0) {
            this.markedContacts.append("; ");
        }
        this.markedContacts.append(contactName);
    }

    private void buildUnsuccessfulMarkString(String contactName) {
        if (this.notMarkedContacts.length() > 0) {
            this.notMarkedContacts.append("; ");
        }
        this.notMarkedContacts.append(contactName);
    }
}
