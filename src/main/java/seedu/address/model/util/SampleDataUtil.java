package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyThanePark;
import seedu.address.model.ThanePark;
import seedu.address.model.ride.Address;
import seedu.address.model.ride.Maintenance;
import seedu.address.model.ride.Name;
import seedu.address.model.ride.Ride;
import seedu.address.model.ride.WaitTime;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ThanePark} with sample data.
 */
public class SampleDataUtil {
    public static Ride[] getSamplePersons() {
        return new Ride[] {
            new Ride(new Name("Alex Yeoh"), new Maintenance("87438807"), new WaitTime("1"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Ride(new Name("Bernice Yu"), new Maintenance("99272758"), new WaitTime("2"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Ride(new Name("Charlotte Oliveiro"), new Maintenance("93210283"), new WaitTime("3"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Ride(new Name("David Li"), new Maintenance("91031282"), new WaitTime("12"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Ride(new Name("Irfan Ibrahim"), new Maintenance("92492021"), new WaitTime("9"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Ride(new Name("Roy Balakrishnan"), new Maintenance("92624417"), new WaitTime("18"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyThanePark getSampleAddressBook() {
        ThanePark sampleAb = new ThanePark();
        for (Ride sampleRide : getSamplePersons()) {
            sampleAb.addRide(sampleRide);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
