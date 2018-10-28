package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RIDES;



import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ride.Address;
import seedu.address.model.ride.Maintenance;
import seedu.address.model.ride.Name;
import seedu.address.model.ride.Ride;
import seedu.address.model.ride.Status;
import seedu.address.model.ride.WaitTime;
import seedu.address.model.tag.Tag;

/**
 * Shut down an existing ride in the address book.
 */
public class ShutDownCommand extends Command{

    public static final String COMMAND_WORD = "shutdown";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Open the ride identified by name.\n "
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " Battlestar";

    public static final String MESSAGE_SHUTDOWN_RIDE_SUCCESS = "Ride is shut down: %1$s";
    public static final String MESSAGE_DUPLICATE_RIDE = "This ride is already shut down.";

    private final Index index;
    private final UpdateRideDescriptor shutdownRideDescriptor;

    /**
     * @param index of the ride in the filtered ride list to open
     */
    public ShutDownCommand(Index index) {
        requireNonNull(index);

        this.index = index;
        this.shutdownRideDescriptor = new UpdateRideDescriptor();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Ride> lastShownList = model.getFilteredRideList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
        }

        Ride rideToOpen = lastShownList.get(index.getZeroBased());
        Ride editedRide = createUpdatedRide(rideToOpen, shutdownRideDescriptor);

        if (!rideToOpen.isSameRide(editedRide) && model.hasPerson(editedRide)) {
            throw new CommandException(MESSAGE_DUPLICATE_RIDE);
        }

        model.updatePerson(rideToOpen, editedRide);
        model.updateFilteredRideList(PREDICATE_SHOW_ALL_RIDES);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SHUTDOWN_RIDE_SUCCESS, editedRide));
    }

    /**
     * Creates and returns a {@code Ride} with the details of {@code rideToOpen}
     * edited with {@code shutdownRideDescriptor}.
     */
    private static Ride createUpdatedRide(Ride rideToOpen, UpdateRideDescriptor openRideDescriptor) {
        assert rideToOpen != null;

        Name updatedName = openRideDescriptor.getName().orElse(rideToOpen.getName());
        Maintenance updatedMaintenance =
                openRideDescriptor.getMaintenance().orElse(rideToOpen.getDaysSinceMaintenance());
        WaitTime updatedWaitTime = openRideDescriptor.getWaitTime().orElse(rideToOpen.getWaitingTime());
        Address updatedAddress = openRideDescriptor.getAddress().orElse(rideToOpen.getAddress());
        Set<Tag> updatedTags = openRideDescriptor.getTags().orElse(rideToOpen.getTags());
        Status updatedStatus = Status.SHUTDOWN;

        return new Ride(updatedName, updatedMaintenance, updatedWaitTime, updatedAddress, updatedTags, updatedStatus);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ShutDownCommand)) {
            return false;
        }

        // state check
        ShutDownCommand e = (ShutDownCommand) other;
        return index.equals(e.index)
                && shutdownRideDescriptor.equals(e.shutdownRideDescriptor);
    }

    /**
     * Stores the details to edit the ride with. Each non-empty field value will replace the
     * corresponding field value of the ride.
     */
    public static class UpdateRideDescriptor {
        private Name name;
        private Maintenance maintenance;
        private WaitTime waitTime;
        private Address address;
        private Set<Tag> tags;
        private Status status;

        public UpdateRideDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public UpdateRideDescriptor(UpdateRideDescriptor toCopy) {
            setName(toCopy.name);
            setMaintenance(toCopy.maintenance);
            setWaitTime(toCopy.waitTime);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setStatus(toCopy.status);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setMaintenance(Maintenance maintenance) {
            this.maintenance = maintenance;
        }

        public Optional<Maintenance> getMaintenance() {
            return Optional.ofNullable(maintenance);
        }

        public void setWaitTime(WaitTime waitTime) {
            this.waitTime = waitTime;
        }

        public Optional<WaitTime> getWaitTime() {
            return Optional.ofNullable(waitTime);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */

        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateRideDescriptor)) {
                return false;
            }

            // state check
            UpdateRideDescriptor e = (UpdateRideDescriptor) other;

            return getName().equals(e.getName())
                    && getMaintenance().equals(e.getMaintenance())
                    && getWaitTime().equals(e.getWaitTime())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags())
                    && getStatus().equals(e.getStatus());
        }
    }
}

