package seedu.address.model.ride;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.ride.exceptions.DuplicateRideException;
import seedu.address.model.ride.exceptions.RideNotFoundException;
import seedu.address.testutil.RideBuilder;

public class UniqueRideListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueRideList uniqueRideList = new UniqueRideList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRideList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueRideList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueRideList.add(ALICE);
        assertTrue(uniqueRideList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueRideList.add(ALICE);
        Ride editedAlice = new RideBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueRideList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRideList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueRideList.add(ALICE);
        thrown.expect(DuplicateRideException.class);
        uniqueRideList.add(ALICE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRideList.setRide(null, ALICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRideList.setRide(ALICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(RideNotFoundException.class);
        uniqueRideList.setRide(ALICE, ALICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueRideList.add(ALICE);
        uniqueRideList.setRide(ALICE, ALICE);
        UniqueRideList expectedUniqueRideList = new UniqueRideList();
        expectedUniqueRideList.add(ALICE);
        assertEquals(expectedUniqueRideList, uniqueRideList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueRideList.add(ALICE);
        Ride editedAlice = new RideBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueRideList.setRide(ALICE, editedAlice);
        UniqueRideList expectedUniqueRideList = new UniqueRideList();
        expectedUniqueRideList.add(editedAlice);
        assertEquals(expectedUniqueRideList, uniqueRideList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueRideList.add(ALICE);
        uniqueRideList.setRide(ALICE, BOB);
        UniqueRideList expectedUniqueRideList = new UniqueRideList();
        expectedUniqueRideList.add(BOB);
        assertEquals(expectedUniqueRideList, uniqueRideList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueRideList.add(ALICE);
        uniqueRideList.add(BOB);
        thrown.expect(DuplicateRideException.class);
        uniqueRideList.setRide(ALICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRideList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(RideNotFoundException.class);
        uniqueRideList.remove(ALICE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueRideList.add(ALICE);
        uniqueRideList.remove(ALICE);
        UniqueRideList expectedUniqueRideList = new UniqueRideList();
        assertEquals(expectedUniqueRideList, uniqueRideList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRideList.setRides((UniqueRideList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueRideList.add(ALICE);
        UniqueRideList expectedUniqueRideList = new UniqueRideList();
        expectedUniqueRideList.add(BOB);
        uniqueRideList.setRides(expectedUniqueRideList);
        assertEquals(expectedUniqueRideList, uniqueRideList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRideList.setRides((List<Ride>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueRideList.add(ALICE);
        List<Ride> rideList = Collections.singletonList(BOB);
        uniqueRideList.setRides(rideList);
        UniqueRideList expectedUniqueRideList = new UniqueRideList();
        expectedUniqueRideList.add(BOB);
        assertEquals(expectedUniqueRideList, uniqueRideList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Ride> listWithDuplicateRides = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateRideException.class);
        uniqueRideList.setRides(listWithDuplicateRides);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRideList.asUnmodifiableObservableList().remove(0);
    }
}
