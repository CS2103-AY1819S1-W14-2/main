package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAINTENANCE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalRides.getTypicalThanePark;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.UpdateCommand.UpdateRideDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ThanePark;
import seedu.address.model.UserPrefs;
import seedu.address.model.ride.Ride;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.RideBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class UpdateCommandTest {

    private Model model = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Ride editedRide = new RideBuilder().build();
        UpdateRideDescriptor descriptor = new EditPersonDescriptorBuilder(editedRide).build();
        UpdateCommand editCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_RIDE_SUCCESS, editedRide);

        Model expectedModel = new ModelManager(new ThanePark(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredRideList().get(0), editedRide);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredRideList().size());
        Ride lastRide = model.getFilteredRideList().get(indexLastPerson.getZeroBased());

        RideBuilder personInList = new RideBuilder(lastRide);
        Ride editedRide = personInList.withName(VALID_NAME_BOB).withMaintenance(VALID_MAINTENANCE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        UpdateRideDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withMaintenance(VALID_MAINTENANCE_BOB).withTags(VALID_TAG_HUSBAND).build();
        UpdateCommand editCommand = new UpdateCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_RIDE_SUCCESS, editedRide);

        Model expectedModel = new ModelManager(new ThanePark(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(lastRide, editedRide);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        UpdateCommand editCommand = new UpdateCommand(INDEX_FIRST_PERSON, new UpdateRideDescriptor());
        Ride editedRide = model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_RIDE_SUCCESS, editedRide);

        Model expectedModel = new ModelManager(new ThanePark(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Ride rideInFilteredList = model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased());
        Ride editedRide = new RideBuilder(rideInFilteredList).withName(VALID_NAME_BOB).build();
        UpdateCommand editCommand = new UpdateCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_RIDE_SUCCESS, editedRide);

        Model expectedModel = new ModelManager(new ThanePark(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredRideList().get(0), editedRide);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Ride firstRide = model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased());
        UpdateRideDescriptor descriptor = new EditPersonDescriptorBuilder(firstRide).build();
        UpdateCommand editCommand = new UpdateCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, UpdateCommand.MESSAGE_DUPLICATE_RIDE);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit ride in filtered list into a duplicate in address book
        Ride rideInList = model.getAddressBook().getRideList().get(INDEX_SECOND_PERSON.getZeroBased());
        UpdateCommand editCommand = new UpdateCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(rideInList).build());

        assertCommandFailure(editCommand, model, commandHistory, UpdateCommand.MESSAGE_DUPLICATE_RIDE);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRideList().size() + 1);
        UpdateRideDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        UpdateCommand editCommand = new UpdateCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getRideList().size());

        UpdateCommand editCommand = new UpdateCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Ride editedRide = new RideBuilder().build();
        Ride rideToEdit = model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased());
        UpdateRideDescriptor descriptor = new EditPersonDescriptorBuilder(editedRide).build();
        UpdateCommand editCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new ThanePark(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(rideToEdit, editedRide);
        expectedModel.commitAddressBook();

        // edit -> first ride edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered ride list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first ride edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRideList().size() + 1);
        UpdateRideDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        UpdateCommand editCommand = new UpdateCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_RIDE_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Ride} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited ride in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the ride object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Ride editedRide = new RideBuilder().buildDifferent();
        UpdateRideDescriptor descriptor = new EditPersonDescriptorBuilder(editedRide).build();
        UpdateCommand editCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new ThanePark(model.getAddressBook()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Ride rideToEdit = model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.updatePerson(rideToEdit, editedRide);
        expectedModel.commitAddressBook();

        // edit -> edits second ride in unfiltered ride list / first ride in filtered ride list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered ride list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased()), rideToEdit);
        // redo -> edits same second ride in unfiltered ride list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final UpdateCommand standardCommand = new UpdateCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        UpdateRideDescriptor copyDescriptor = new UpdateCommand.UpdateRideDescriptor(DESC_AMY);
        UpdateCommand commandWithSameValues = new UpdateCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
