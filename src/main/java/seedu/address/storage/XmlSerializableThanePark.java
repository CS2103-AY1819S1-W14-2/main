package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyThanePark;
import seedu.address.model.ThanePark;
import seedu.address.model.ride.Ride;

/**
 * An Immutable ThanePark that is serializable to XML format
 */
@XmlRootElement(name = "thanepark")
public class XmlSerializableThanePark {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate ride(s).";

    @XmlElement
    private List<XmlAdaptedRide> rides;

    /**
     * Creates an empty XmlSerializableThanePark.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableThanePark() {
        rides = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableThanePark(ReadOnlyThanePark src) {
        this();
        rides.addAll(src.getRideList().stream().map(XmlAdaptedRide::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code ThanePark} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedRide}.
     */
    public ThanePark toModelType() throws IllegalValueException {
        ThanePark addressBook = new ThanePark();
        for (XmlAdaptedRide p : rides) {
            Ride ride = p.toModelType();
            if (addressBook.hasRide(ride)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addRide(ride);
        }
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableThanePark)) {
            return false;
        }
        return rides.equals(((XmlSerializableThanePark) other).rides);
    }
}
