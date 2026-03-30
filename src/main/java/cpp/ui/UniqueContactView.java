package cpp.ui;

import java.util.List;
import java.util.stream.Collectors;

import cpp.logic.Messages;
import cpp.model.assignment.ContactAssignment;
import cpp.model.assignment.ContactAssignmentWithAssignment;
import cpp.model.contact.Contact;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * UI component that displays full details for a single contact and related
 * assignment data.
 */
public class UniqueContactView extends UiPart<Region> {
    private static final String FXML = "UniqueContactView.fxml";

    @FXML
    private Label contactName;

    @FXML
    private Label contactPhone;

    @FXML
    private Label contactEmail;

    @FXML
    private Label contactAddress;

    @FXML
    private StackPane contactAssignmentsPlaceholder;

    private ListView<String> relatedAssignmentsListView;

    public UniqueContactView() {
        super(UniqueContactView.FXML);
    }

    /**
     * Sets the contact details shown in this view.
     */
    public void setContact(Contact contact) {
        this.contactName.setText(contact.getName().fullName);
        this.contactPhone.setText(contact.getPhone().value);
        this.contactEmail.setText(contact.getEmail().value);
        this.contactAddress.setText(contact.getAddress().value);
    }

    /**
     * Sets the contact details and related assignment data.
     */
    public void setContact(Contact contact, List<ContactAssignmentWithAssignment> cas) {
        this.setContact(contact);

        List<String> assignmentLines = cas == null
                ? List.of()
                : cas.stream().map(this::formatContactAssignment).collect(Collectors.toList());

        if (assignmentLines.isEmpty()) {
            assignmentLines = List.of("No related assignments");
        }

        if (this.relatedAssignmentsListView != null) {
            this.contactAssignmentsPlaceholder.getChildren().clear();
        }

        this.relatedAssignmentsListView = new ListView<>(FXCollections.observableArrayList(assignmentLines));
        this.contactAssignmentsPlaceholder.getChildren().add(this.relatedAssignmentsListView);
    }

    private String formatContactAssignment(ContactAssignmentWithAssignment caWithAssignment) {
        ContactAssignment ca = caWithAssignment.getContactAssignment();
        String assignmentText = caWithAssignment.getAssignment() == null
                ? ca.getAssignmentId()
                : Messages.format(caWithAssignment.getAssignment());

        return assignmentText + "; Submitted: " + ca.isSubmitted() + "; Graded: " + ca.isGraded();
    }
}
