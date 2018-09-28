package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.ride.Ride;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyThanePark {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Ride> getRideList();

}
