package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.ride.Ride;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Ride> PREDICATE_SHOW_ALL_RIDES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyThanePark newData);

    /** Returns the ThanePark */
    ReadOnlyThanePark getThanePark();

    /**
     * Returns true if a ride with the same identity as {@code ride} exists in the address book.
     */
    boolean hasRide(Ride ride);

    /**
     * Deletes the given ride.
     * The ride must exist in the address book.
     */
    void deleteRide(Ride target);

    /**
     * Adds the given ride.
     * {@code ride} must not already exist in the address book.
     */
    void addRide(Ride ride);

    /**
     * Replaces the given ride {@code target} with {@code editedRide}.
     * {@code target} must exist in the address book.
     * The ride identity of {@code editedRide} must not be the same as another existing ride in the address book.
     */
    void updateRide(Ride target, Ride editedRide);

    /** Returns an unmodifiable view of the filtered ride list */
    ObservableList<Ride> getFilteredRideList();

    /**
     * Updates the filter of the filtered ride list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRideList(Predicate<Ride> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoThanePark();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoThanePark();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoThanePark();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoThanePark();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitThanePark();
}
