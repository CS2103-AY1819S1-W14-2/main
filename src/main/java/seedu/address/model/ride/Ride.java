package seedu.address.model.ride;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Ride in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Ride {

    // Identity fields
    private final Name name;
    private final Maintenance daysSinceMaintenance;
    private final WaitTime waitingTime;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    //Current state
    private Status status;

    /**
     * Every field must be present and not null. Default value of status is SHUTDOWN
     */
    public Ride(Name name, Maintenance daysSinceMaintenance, WaitTime waitingTime, Address address, Set<Tag> tags) {
        requireAllNonNull(name, daysSinceMaintenance, waitingTime, address, tags);
        this.name = name;
        this.daysSinceMaintenance = daysSinceMaintenance;
        this.waitingTime = waitingTime;
        this.address = address;
        this.tags.addAll(tags);

        this.status = Status.SHUTDOWN;
    }

    public Name getName() {
        return name;
    }

    public WaitTime getWaitingTime() {
        return waitingTime;
    }

    public Maintenance getDaysSinceMaintenance() {
        return daysSinceMaintenance;
    }

    public Status getStatus() {
        return status;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void setDaysSinceMaintenance(int value) {
        this.daysSinceMaintenance.setValue(value);
    }

    public void setWaitingTime(int value) {
        this.waitingTime.setValue(value);
    }

    /**
     * Returns true if both rides have the same name.
     * This defines a weaker notion of equality between two rides.
     */
    public boolean isSameRide(Ride otherRide) {
        if (otherRide == this) {
            return true;
        }

        return otherRide != null
                && otherRide.getName().equals(getName());
    }

    /**
     * Returns true if both rides have the same identity and data fields.
     * This defines a stronger notion of equality between two rides.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Ride)) {
            return false;
        }

        Ride otherRide = (Ride) other;
        return otherRide.getName().equals(getName())
                && otherRide.getDaysSinceMaintenance().equals(getDaysSinceMaintenance())
                && otherRide.getWaitingTime().equals(getWaitingTime())
                && otherRide.getAddress().equals(getAddress())
                && otherRide.getStatus() == getStatus()
                && otherRide.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, daysSinceMaintenance, waitingTime, address, status, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Maintenance: ")
                .append(getDaysSinceMaintenance())
                .append(" WaitTime: ")
                .append(getWaitingTime())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
