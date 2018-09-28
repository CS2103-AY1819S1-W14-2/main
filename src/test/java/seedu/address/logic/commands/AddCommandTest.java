package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyThanePark;
import seedu.address.model.ThanePark;
import seedu.address.model.ride.Ride;
import seedu.address.testutil.RideBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Ride validRide = new RideBuilder().build();

        CommandResult commandResult = new AddCommand(validRide).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validRide), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validRide), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Ride validRide = new RideBuilder().build();
        AddCommand addCommand = new AddCommand(validRide);
        ModelStub modelStub = new ModelStubWithPerson(validRide);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Ride alice = new RideBuilder().withName("Alice").build();
        Ride bob = new RideBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different ride -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Ride ride) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyThanePark newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyThanePark getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Ride ride) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Ride target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Ride target, Ride editedRide) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Ride> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Ride> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single ride.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Ride ride;

        ModelStubWithPerson(Ride ride) {
            requireNonNull(ride);
            this.ride = ride;
        }

        @Override
        public boolean hasPerson(Ride ride) {
            requireNonNull(ride);
            return this.ride.isSameRide(ride);
        }
    }

    /**
     * A Model stub that always accept the ride being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Ride> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Ride ride) {
            requireNonNull(ride);
            return personsAdded.stream().anyMatch(ride::isSameRide);
        }

        @Override
        public void addPerson(Ride ride) {
            requireNonNull(ride);
            personsAdded.add(ride);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyThanePark getAddressBook() {
            return new ThanePark();
        }
    }

}
