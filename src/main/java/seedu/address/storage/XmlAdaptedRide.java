package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ride.Address;
import seedu.address.model.ride.Maintenance;
import seedu.address.model.ride.Name;
import seedu.address.model.ride.Ride;
import seedu.address.model.ride.WaitTime;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Ride.
 */
public class XmlAdaptedRide {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ride's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String daysSinceMaintenanceString;
    @XmlElement(required = true)
    private String waitingTimeString;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedRide.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRide() {}

    /**
     * Constructs an {@code XmlAdaptedRide} with the given ride details.
     */
    public XmlAdaptedRide(String name, String daysSinceMaintenanceString, String waitingTimeString, String address,
                          List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.daysSinceMaintenanceString = daysSinceMaintenanceString;
        this.waitingTimeString = waitingTimeString;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Ride into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRide
     */
    public XmlAdaptedRide(Ride source) {
        name = source.getName().fullName;
        daysSinceMaintenanceString = source.getDaysSinceMaintenance().toString();
        waitingTimeString = source.getWaitingTime().toString();
        address = source.getAddress().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted ride object into the model's Ride object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ride
     */
    public Ride toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (daysSinceMaintenanceString == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Maintenance.class.getSimpleName()));
        }
        if (!Maintenance.isValidMaintenance(daysSinceMaintenanceString)) {
            throw new IllegalValueException(Maintenance.MESSAGE_MAINTENANCE_CONSTRAINTS);
        }
        final Maintenance modelMaintenance = new Maintenance(daysSinceMaintenanceString);

        if (waitingTimeString == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, WaitTime.class.getSimpleName()));
        }
        if (!WaitTime.isValidWaitTime(waitingTimeString)) {
            throw new IllegalValueException(WaitTime.MESSAGE_WAIT_TIME_CONSTRAINTS);
        }
        final WaitTime modelWaitTime = new WaitTime(waitingTimeString);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Ride(modelName, modelMaintenance, modelWaitTime, modelAddress, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRide)) {
            return false;
        }

        XmlAdaptedRide otherPerson = (XmlAdaptedRide) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(daysSinceMaintenanceString, otherPerson.daysSinceMaintenanceString)
                && Objects.equals(waitingTimeString, otherPerson.waitingTimeString)
                && Objects.equals(address, otherPerson.address)
                && tagged.equals(otherPerson.tagged);
    }
}
