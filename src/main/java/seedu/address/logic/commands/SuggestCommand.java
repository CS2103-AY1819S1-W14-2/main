package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SuggestCommandEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Not a text command. Can only be triggered using {tab} in CommandBox.
 * Suggests a list of commands based on prefix matching.
 */
public class SuggestCommand extends Command {

    public static final String[] COMMAND_WORDS = {AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ViewAllCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,
        SelectCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD};
    public static final String MESSAGE_SUGGEST_COMMAND_SUCCESS = "Do you mean the following commands:\n%1$s";

    private final String[] suggestions;

    public SuggestCommand(String prefix) {
        requireNonNull(prefix);
        if (!prefix.matches("[a-zA-Z]+")) {
            suggestions = new String[0];
            return;
        }
        suggestions = Arrays.stream(COMMAND_WORDS).filter(s
            -> s.matches("^" + prefix + ".*")).toArray(String[]::new);
    }

    public static String combineCommandWords(String... commandWords) {
        return String.join(", ", commandWords);
    }

    public boolean isPrefixValid() {
        return suggestions.length > 0;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        String messageSuccess = String.format(MESSAGE_SUGGEST_COMMAND_SUCCESS, combineCommandWords(suggestions));
        EventsCenter.getInstance().post(new SuggestCommandEvent(suggestions));
        return new CommandResult(messageSuccess);
    }
}
