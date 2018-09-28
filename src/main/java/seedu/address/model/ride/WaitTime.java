package seedu.address.model.ride;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Ride's current waiting time, in minutes, in the park management app.
 */
public class WaitTime {


    public static final String MESSAGE_WAIT_TIME_CONSTRAINTS =
            "Waiting time should only contain numbers with at least 1 digit long.";
    public static final String WAIT_TIME_VALIDATION_REGEX = "\\d+";
    private int value;

    /**
     * Constructs a {@code WaitTime}.
     *
     * @param waitingTime Waiting time in minutes.
     */
    public WaitTime(String waitingTime) {
        requireNonNull(waitingTime);
        checkArgument(isValidWaitTime(waitingTime), MESSAGE_WAIT_TIME_CONSTRAINTS);
        value = Integer.parseInt(waitingTime);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid days since last maintenance.
     */
    public static boolean isValidWaitTime(String test) {
        return test.matches(WAIT_TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WaitTime // instanceof handles nulls
                && value == ((WaitTime) other).value); // state check
    }

    @Override
    public int hashCode() {
        return value;
    }

}
