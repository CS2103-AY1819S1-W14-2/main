package seedu.address.model.ride;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAINTENANCE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAIT_TIME_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.RideBuilder;

public class RideTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Ride ride = new RideBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        ride.getTags().remove(0);
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameRide(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameRide(null));

        // different phone and email but same name -> returns true
        Ride editedAlice = new RideBuilder(ALICE)
                .withMaintenance(VALID_MAINTENANCE_BOB)
                .withWaitTime(VALID_WAIT_TIME_BOB)
                .build();
        assertTrue(ALICE.isSameRide(editedAlice));

        // different name -> returns false
        editedAlice = new RideBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameRide(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new RideBuilder(ALICE).withWaitTime(VALID_WAIT_TIME_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameRide(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new RideBuilder(ALICE).withMaintenance(VALID_MAINTENANCE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameRide(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new RideBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameRide(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Ride aliceCopy = new RideBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different ride -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Ride editedAlice = new RideBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new RideBuilder(ALICE).withMaintenance(VALID_MAINTENANCE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new RideBuilder(ALICE).withWaitTime(VALID_WAIT_TIME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new RideBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new RideBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
