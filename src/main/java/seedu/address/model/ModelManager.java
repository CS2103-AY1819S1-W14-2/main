package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ThaneParkChangedEvent;
import seedu.address.model.ride.Ride;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedThanePark versionedAddressBook;
    private final FilteredList<Ride> filteredRides;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyThanePark addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedThanePark(addressBook);
        filteredRides = new FilteredList<>(versionedAddressBook.getRideList());
    }

    public ModelManager() {
        this(new ThanePark(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyThanePark newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyThanePark getAddressBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new ThaneParkChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasPerson(Ride ride) {
        requireNonNull(ride);
        return versionedAddressBook.hasRide(ride);
    }

    @Override
    public void deletePerson(Ride target) {
        versionedAddressBook.removeRide(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Ride ride) {
        versionedAddressBook.addRide(ride);
        updateFilteredRideList(PREDICATE_SHOW_ALL_RIDES);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Ride target, Ride editedRide) {
        requireAllNonNull(target, editedRide);

        versionedAddressBook.updateRide(target, editedRide);
        indicateAddressBookChanged();
    }

    //=========== Filtered Ride List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Ride} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Ride> getFilteredRideList() {
        return FXCollections.unmodifiableObservableList(filteredRides);
    }

    @Override
    public void updateFilteredRideList(Predicate<Ride> predicate) {
        requireNonNull(predicate);
        filteredRides.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredRides.equals(other.filteredRides);
    }

}
