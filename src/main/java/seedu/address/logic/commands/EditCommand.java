package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAINTENANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAITING_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RIDES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ride.Address;
import seedu.address.model.ride.Maintenance;
import seedu.address.model.ride.Name;
import seedu.address.model.ride.Ride;
import seedu.address.model.ride.WaitTime;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing ride in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the ride identified "
            + "by the index number used in the displayed ride list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_MAINTENANCE + "PHONE] "
            + "[" + PREFIX_WAITING_TIME + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MAINTENANCE + "91234567 "
            + PREFIX_WAITING_TIME + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Ride: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This ride already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the ride in the filtered ride list to edit
     * @param editPersonDescriptor details to edit the ride with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Ride> lastShownList = model.getFilteredRideList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Ride rideToEdit = lastShownList.get(index.getZeroBased());
        Ride editedRide = createEditedPerson(rideToEdit, editPersonDescriptor);

        if (!rideToEdit.isSameRide(editedRide) && model.hasPerson(editedRide)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.updatePerson(rideToEdit, editedRide);
        model.updateFilteredRideList(PREDICATE_SHOW_ALL_RIDES);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedRide));
    }

    /**
     * Creates and returns a {@code Ride} with the details of {@code rideToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Ride createEditedPerson(Ride rideToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert rideToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(rideToEdit.getName());
        Maintenance updatedMaintenance =
                editPersonDescriptor.getMaintenance().orElse(rideToEdit.getDaysSinceMaintenance());
        WaitTime updatedWaitTime = editPersonDescriptor.getWaitTime().orElse(rideToEdit.getWaitingTime());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(rideToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(rideToEdit.getTags());

        return new Ride(updatedName, updatedMaintenance, updatedWaitTime, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the ride with. Each non-empty field value will replace the
     * corresponding field value of the ride.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Maintenance maintenance;
        private WaitTime waitTime;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setMaintenance(toCopy.maintenance);
            setWaitTime(toCopy.waitTime);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, maintenance, waitTime, address, tags);
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

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getMaintenance().equals(e.getMaintenance())
                    && getWaitTime().equals(e.getWaitTime())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
