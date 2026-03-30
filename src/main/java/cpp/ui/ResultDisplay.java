package cpp.ui;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    /**
     * Creates a {@code ResultDisplay} and wires automatic height adjustment
     * listeners.
     */
    public ResultDisplay() {
        super(ResultDisplay.FXML);
        this.resultDisplay.setWrapText(true);
    }

    /**
     * Updates the feedback text shown to the user.
     *
     * @param feedbackToUser feedback message to display
     */
    public void setFeedbackToUser(String feedbackToUser) {
        Objects.requireNonNull(feedbackToUser);
        this.resultDisplay.setText(feedbackToUser);
    }

}
