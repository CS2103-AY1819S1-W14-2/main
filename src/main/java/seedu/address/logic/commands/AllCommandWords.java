package seedu.address.logic.commands;

/***
 * A class containing all command words in ThanePark
 */
public class AllCommandWords {
    public static final String[] COMMAND_WORDS = {AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD, UpdateCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, QuickViewCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,
        ViewAllCommand.COMMAND_WORD, ViewCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD};

    /**
     * Checks if the given commandWord is valid
     */
    public static boolean isCommandWord(String commandWord) {
        boolean containsCommandWord = false;
        for (String referenceWord : COMMAND_WORDS) {
            containsCommandWord = containsCommandWord || commandWord.equals(referenceWord);
        }
        return containsCommandWord;
    }
}
