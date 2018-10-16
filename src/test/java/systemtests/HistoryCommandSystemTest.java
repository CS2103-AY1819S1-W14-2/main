package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.HistoryWindowHandle;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.ui.StatusBarFooter;

/**
 * A system test class for the history window, which contains interaction with other UI components.
 */
public class HistoryCommandSystemTest extends AddressBookSystemTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openHistoryWindow() {
        //use command box
        executeCommand(HistoryCommand.COMMAND_WORD + " more");
        assertHistoryWindowOpen();

        // open history window and give it focus
        executeCommand(HistoryCommand.COMMAND_WORD + " more");
        getMainWindowHandle().focus();

        // assert that while the history window is open the UI updates correctly for a command execution
        executeCommand(ViewCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals("", getCommandBox().getInput());
        assertCommandBoxShowsDefaultStyle();
        assertNotEquals(HistoryCommand.MESSAGE_HISTORY_WINDOW, getResultDisplay().getText());
        assertListMatching(getPersonListPanel(), getModel().getFilteredRideList());
        // assert that the status bar too is updated correctly while the history window is open
        // note: the select command tested above does not update the status bar
        executeCommand(DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertNotEquals(StatusBarFooter.SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    @Test
    public void history_multipleCommands_onlyOneHistoryWindowOpen() {
        getMainWindowHandle().focus();
        executeCommand(HistoryCommand.COMMAND_WORD + " more");

        getMainWindowHandle().focus();
        executeCommand(HistoryCommand.COMMAND_WORD + " more");

        assertEquals(1, guiRobot.getNumberOfWindowsShown(HistoryWindowHandle.HISTORY_WINDOW_TITLE));
    }

    /**
     * Asserts that the history window is open, and closes it after checking.
     */
    private void assertHistoryWindowOpen() {
        assertTrue(ERROR_MESSAGE, HistoryWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new HistoryWindowHandle(guiRobot.getStage(HistoryWindowHandle.HISTORY_WINDOW_TITLE)).close();
        getMainWindowHandle().focus();
    }

    /**
     * Asserts that the history window isn't open.
     */
    private void assertHistoryWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, HistoryWindowHandle.isWindowPresent());
    }

}
