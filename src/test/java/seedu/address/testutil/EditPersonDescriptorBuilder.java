package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.ride.Address;
import seedu.address.model.ride.Maintenance;
import seedu.address.model.ride.Name;
import seedu.address.model.ride.Ride;
import seedu.address.model.ride.WaitTime;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code ride}'s details
     */
    public EditPersonDescriptorBuilder(Ride ride) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(ride.getName());
        descriptor.setMaintenance(ride.getDaysSinceMaintenance());
        descriptor.setWaitTime(ride.getWaitingTime());
        descriptor.setAddress(ride.getAddress());
        descriptor.setTags(ride.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Maintenance} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withMaintenance(String daysSinceMaintenanceString) {
        descriptor.setMaintenance(new Maintenance(daysSinceMaintenanceString));
        return this;
    }

    /**
     * Sets the {@code WaitTime} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withWaitTime(String email) {
        descriptor.setWaitTime(new WaitTime(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
