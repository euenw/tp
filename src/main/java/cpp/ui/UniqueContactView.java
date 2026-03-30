package cpp.ui;

import java.util.List;

import cpp.model.assignment.ContactAssignmentWithAssignment;
import cpp.model.classgroup.ClassGroup;
import cpp.model.contact.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * UI component that displays full details for a single contact and related
 * assignment data.
 */
public class UniqueContactView extends UiPart<Region> {
    private static final String FXML = "UniqueContactView.fxml";
    private static final int MAX_VISIBLE_ROWS = 3;
    private static final double CARD_ROW_HEIGHT = 105.0;

    @FXML
    private Label contactName;

    @FXML
    private Label contactPhone;

    @FXML
    private Label contactEmail;

    @FXML
    private Label contactAddress;

    @FXML
    private StackPane contactClassGroupsPlaceholder;

    @FXML
    private StackPane contactAssignmentsPlaceholder;

    private ContactAssignmentAssignmentListPanel assignmentListPanel;
    private ClassGroupListPanel classGroupListPanel;

    /**
     * Creates a {@code UniqueContactView}.
     */
    public UniqueContactView() {
        super(UniqueContactView.FXML);
        this.resizeBlock(this.contactClassGroupsPlaceholder, 0);
        this.resizeBlock(this.contactAssignmentsPlaceholder, 0);
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
        this.setContact(contact, cas, List.of());
    }

    /**
     * Sets the contact details, related assignment data, and class groups.
     */
    public void setContact(Contact contact, List<ContactAssignmentWithAssignment> cas,
            List<ClassGroup> classGroups) {
        this.contactName.setText(contact.getName().fullName);
        this.contactPhone.setText(contact.getPhone().value);
        this.contactEmail.setText(contact.getEmail().value);
        this.contactAddress.setText(contact.getAddress().value);

        List<ClassGroup> safeClassGroups = classGroups == null ? List.of() : classGroups;
        List<ContactAssignmentWithAssignment> safeCas = cas == null ? List.of() : cas;

        this.resizeBlock(this.contactClassGroupsPlaceholder, safeClassGroups.size());
        this.resizeBlock(this.contactAssignmentsPlaceholder, safeCas.size());

        if (safeClassGroups.isEmpty()) {
            this.contactClassGroupsPlaceholder.getChildren().clear();
        }

        ObservableList<ClassGroup> observableClassGroups = FXCollections
                .observableArrayList(safeClassGroups);
        if (!safeClassGroups.isEmpty() && this.classGroupListPanel != null) {
            this.contactClassGroupsPlaceholder.getChildren().clear();
        }
        if (!safeClassGroups.isEmpty()) {
            this.classGroupListPanel = new ClassGroupListPanel(observableClassGroups);
            this.contactClassGroupsPlaceholder.getChildren().add(this.classGroupListPanel.getRoot());
        }

        if (safeCas.isEmpty()) {
            this.contactAssignmentsPlaceholder.getChildren().clear();
        }

        ObservableList<ContactAssignmentWithAssignment> observableCas = FXCollections
                .observableArrayList(safeCas);
        if (!safeCas.isEmpty() && this.assignmentListPanel != null) {
            this.contactAssignmentsPlaceholder.getChildren().clear();
        }
        if (!safeCas.isEmpty()) {
            this.assignmentListPanel = new ContactAssignmentAssignmentListPanel(observableCas);
            this.contactAssignmentsPlaceholder.getChildren().add(this.assignmentListPanel.getRoot());
        }
    }

    private void resizeBlock(StackPane block, int itemCount) {
        double targetHeight = this.computeBlockHeight(itemCount);
        block.setMinHeight(targetHeight);
        block.setPrefHeight(targetHeight);
        block.setMaxHeight(targetHeight);
    }

    private double computeBlockHeight(int itemCount) {
        if (itemCount <= 0) {
            return 0.0;
        }
        return Math.min(itemCount, UniqueContactView.MAX_VISIBLE_ROWS) * UniqueContactView.CARD_ROW_HEIGHT;
    }
}
