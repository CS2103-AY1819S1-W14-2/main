package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ThanePark;
import seedu.address.model.UserPrefs;
import seedu.address.model.ride.Ride;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.RideBuilder;

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute() {
        //setup
        deleteFirstPerson(model);
        deleteFirstPerson(model);

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);

        // multiple undoable states in model
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void undoRedoCommandsAreIncluded() throws Exception {
        //includes add, clear, delete and edit
        CommandHistory commandHistory = new CommandHistory();
        int total = 0;

        Ride validRide = new RideBuilder().buildDifferent();
        new AddCommand(validRide).execute(model, commandHistory);
        expectedModel.addPerson(validRide);
        expectedModel.commitAddressBook();
        assertEquals(expectedModel, model);
        total++;

        Ride rideToDelete = model.getFilteredRideList().get(INDEX_FIRST_PERSON.getZeroBased());
        new DeleteCommand(INDEX_FIRST_PERSON).execute(model, commandHistory);
        expectedModel.deletePerson(rideToDelete);
        expectedModel.commitAddressBook();
        assertEquals(expectedModel, model);
        total++;

        //Change some ride back to the deleted one
        Ride editedRide = rideToDelete;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedRide).build();
        expectedModel.updatePerson(model.getFilteredRideList().get(0), editedRide);
        new EditCommand(INDEX_FIRST_PERSON, descriptor).execute(model, commandHistory);
        expectedModel.commitAddressBook();
        assertEquals(expectedModel, model);
        total++;

        expectedModel.resetData(new ThanePark());
        expectedModel.commitAddressBook();
        new ClearCommand().execute(model, commandHistory);
        assertEquals(expectedModel, model);
        total++;

        for (int i = 0; i < total; i++) {
            expectedModel.undoAddressBook();
            assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        }

        for (int i = 0; i < total; i++) {
            expectedModel.redoAddressBook();
            assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        }
    }

}
