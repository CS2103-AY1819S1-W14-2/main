package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.SuggestCommand.MESSAGE_SUGGEST_COMMAND_SUCCESS;

import org.junit.Test;

import seedu.address.logic.commands.SuggestCommand;
import seedu.address.model.Model;

/**
 * Contains system tests for {@code SuggestCommand}
 */
public class SuggestCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void suggestCommand() throws Exception {
        assertSuggestCommandSuccessBehavior("a", "add");
        assertSuggestCommandSuccessBehavior("c", "clear");
        assertSuggestCommandSuccessBehavior("d", "delete");
        assertSuggestCommandSuccessBehavior("e", "exit");
        assertSuggestCommandSuccessBehavior("f", "find");
        assertSuggestCommandSuccessBehavior("h", "help", "history");
        assertSuggestCommandSuccessBehavior("q", "quickView");
        assertSuggestCommandSuccessBehavior("r", "redo");
        assertSuggestCommandSuccessBehavior("u", "undo");
        assertSuggestCommandSuccessBehavior("u", "update", "undo");
        assertSuggestCommandSuccessBehavior("v", "viewAll", "view");

        assertSuggestCommandFailureBehavior("b");
        assertSuggestCommandFailureBehavior("g");
        assertSuggestCommandFailureBehavior("i");
        assertSuggestCommandFailureBehavior("j");
        assertSuggestCommandFailureBehavior("k");
        assertSuggestCommandFailureBehavior("l");
        assertSuggestCommandFailureBehavior("m");
        assertSuggestCommandFailureBehavior("n");
        assertSuggestCommandFailureBehavior("o");
        assertSuggestCommandFailureBehavior("p");
        assertSuggestCommandFailureBehavior("s");
        assertSuggestCommandFailureBehavior("t");
        assertSuggestCommandFailureBehavior("w");
        assertSuggestCommandFailureBehavior("x");
        assertSuggestCommandFailureBehavior("y");
        assertSuggestCommandFailureBehavior("z");
    }

    /**
     * The commandResult of pressing tab is tested by SuggestCommandTest
     */
    @Test
    public void handleKeyPress_tab() throws Exception {
        //TODO: Assert that commands are valid or invalid
        //Check that tab does not do anything on an invalid command
        assertTabBehavior("fail", "fail");

        //Check that tab does not execute commands
        assertTabBehavior("help", "help");

        //Check that tab does complete commands
        assertTabBehavior("dele", "delete");

        //Check that tab does not complete when there are multiple commands
        assertTabBehavior("h", "h");
    }


    /**
     * Performs UI visible verification of suggest command behavior resulting in 1 or more suggested commands.
     * Logic tests are done by SuggestCommandTest
     * @see seedu.address.logic.commands.SuggestCommandTest
     */
    private void assertSuggestCommandSuccessBehavior(String command, String... commandWords) throws Exception {
        String expectedResultMessage = String.format(MESSAGE_SUGGEST_COMMAND_SUCCESS,
                SuggestCommand.combineCommandWords(commandWords));
        String expectedCommandBoxText;
        if (commandWords.length == 1) {
            expectedCommandBoxText = commandWords[0];
        } else if (commandWords.length > 1) {
            expectedCommandBoxText = "";
        } else {
            throw new Exception("Wrong use of assertCommandSuccess!");
        }
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(expectedCommandBoxText, expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Performs UI visible verification of suggest command behavior resulting in no suggested commands.
     * Logic tests are done by SuggestCommandTest
     * @see seedu.address.logic.commands.SuggestCommandTest
     */
    private void assertSuggestCommandFailureBehavior(String command) {
        String expectedResultMessage = MESSAGE_UNKNOWN_COMMAND;
        String expectedCommandBoxText = command;
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(expectedCommandBoxText, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
    }

    /**
     * Checks the behavior of tab inside the commandBox
     */
    private void assertTabBehavior(String input, String expectedCommandBoxText) {
        getCommandBox().setText(input);
        getCommandBox().pressTab();
        assertEquals(expectedCommandBoxText, getCommandBox().getInput());
        assertTrue(getCommandBox().isFocused());
    }

}
