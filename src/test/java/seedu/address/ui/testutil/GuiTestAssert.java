package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.ride.Ride;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getWaitingTime(), actualCard.getWaitingTime());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getMaintenance(), actualCard.getMaintenance());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedRide}.
     */
    public static void assertCardDisplaysPerson(Ride expectedRide, PersonCardHandle actualCard) {
        assertEquals(expectedRide.getName().fullName, actualCard.getName());
        assertEquals(expectedRide.getDaysSinceMaintenance().toString(), actualCard.getMaintenance());
        assertEquals(expectedRide.getWaitingTime().toString(), actualCard.getWaitingTime());
        assertEquals(expectedRide.getAddress().value, actualCard.getAddress());
        assertEquals(expectedRide.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code rides} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Ride... rides) {
        for (int i = 0; i < rides.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(rides[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code rides} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Ride> rides) {
        assertListMatching(personListPanelHandle, rides.toArray(new Ride[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
