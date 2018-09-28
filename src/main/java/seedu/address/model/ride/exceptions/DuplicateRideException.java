package seedu.address.model.ride.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateRideException extends RuntimeException {
    public DuplicateRideException() {
        super("Operation would result in duplicate persons");
    }
}
