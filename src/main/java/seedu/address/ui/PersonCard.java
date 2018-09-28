package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.ride.Ride;

/**
 * An UI component that displays information of a {@code Ride}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

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
    private FlowPane tags;

    public PersonCard(Ride ride, int displayedIndex) {
        super(FXML);
        this.ride = ride;
        id.setText(displayedIndex + ". ");
        name.setText(ride.getName().fullName);
        daysSinceMaintenanceString.setText(ride.getDaysSinceMaintenance().toString());
        address.setText(ride.getAddress().value);
        waitingTimeString.setText(ride.getWaitingTime().toString());
        ride.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && ride.equals(card.ride);
    }
}
