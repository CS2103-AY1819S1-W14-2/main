package seedu.address.testutil;

import seedu.address.model.ThanePark;
import seedu.address.model.ride.Ride;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ThanePark ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private ThanePark addressBook;

    public AddressBookBuilder() {
        addressBook = new ThanePark();
    }

    public AddressBookBuilder(ThanePark addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Ride} to the {@code ThanePark} that we are building.
     */
    public AddressBookBuilder withPerson(Ride ride) {
        addressBook.addRide(ride);
        return this;
    }

    public ThanePark build() {
        return addressBook;
    }
}
