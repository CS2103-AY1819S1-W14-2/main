package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.ride.Ride;
import seedu.address.testutil.RideBuilder;

public class RideCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Ride rideWithNoTags = new RideBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(rideWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, rideWithNoTags, 1);

        // with tags
        Ride rideWithTags = new RideBuilder().build();
        personCard = new PersonCard(rideWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, rideWithTags, 2);
    }

    @Test
    public void equals() {
        Ride ride = new RideBuilder().build();
        PersonCard personCard = new PersonCard(ride, 0);

        // same ride, same index -> returns true
        PersonCard copy = new PersonCard(ride, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different ride, same index -> returns false
        Ride differentRide = new RideBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentRide, 0)));

        // same ride, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(ride, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedRide} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Ride expectedRide, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify ride details are displayed correctly
        assertCardDisplaysPerson(expectedRide, personCardHandle);
    }
}
