package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.ride.Ride;
import seedu.address.model.ride.Status;

/**
 * An UI component that displays information of a {@code Ride}.
 */
public class RideCard extends UiPart<Region> {

    private static final String FXML = "RideListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ThanePark level 4</a>
     */

    public final Ride ride;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label daysSinceMaintenanceString;
    @FXML
    private Label address;
    @FXML
    private Label waitingTimeString;
    @FXML
    private Label statusString;
    @FXML
    private FlowPane tags;

    public RideCard(Ride ride, int displayedIndex) {
        super(FXML);
        this.ride = ride;
        id.setText(displayedIndex + ". ");
        name.setText(ride.getName().fullName);
        daysSinceMaintenanceString.setText(ride.getDaysSinceMaintenance().toString());
        address.setText(ride.getAddress().value);
        waitingTimeString.setText(ride.getWaitingTime().toString());
        statusString.setText(ride.getStatus().name());
        if (ride.getStatus().equals(Status.OPEN)) {
            statusString.setStyle("-fx-background-color: #17A828");
            //statusString.setTextFill(Color.web("#dd3333"));
        } else if (ride.getStatus().equals(Status.SHUTDOWN)
            || ride.getStatus().equals(Status.MAINTENANCE)) {
            statusString.setStyle("-fx-background-color: #CC3045");
            //statusString.setTextFill(Color.web("33dd33"));
        //error
        } else {
        }
        ride.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RideCard)) {
            return false;
        }

        // state check
        RideCard card = (RideCard) other;
        return id.getText().equals(card.id.getText())
                && ride.equals(card.ride);
    }
}
