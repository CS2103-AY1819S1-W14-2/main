package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.HtmlReportGenerator;
import seedu.address.commons.events.ui.ShowHistoryRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows history of executed commands.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MORE_INFO_FLAG = "more";

    public static final String MESSAGE_SUCCESS = "Entered commands (from most recent to earliest):\n%1$s";
    public static final String MESSAGE_HISTORY_WINDOW = "Showing requested history of commands.";
    public static final String MESSAGE_HISTORY_WINDOW_FAILURE = "Failed to generate Html report.";
    public static final String MESSAGE_NO_HISTORY = "You have not yet entered any commands.";

    private static final String[] ARGUMENTS_BLANK = {""};
    private final boolean isSummarized;

    /**
     * Creates a HistoryCommand.
     */
    public HistoryCommand() {
        this(ARGUMENTS_BLANK);
    }

    /**
     * Creates a HistoryCommand that requests for help based on {@param args}.
     */
    public HistoryCommand(String[] args) {
        //summarized history
        if (args.length == 1 && args[0].isEmpty()) {
            isSummarized = true;
        //full history
        } else if (args.length == 1 && args[0].equals(MORE_INFO_FLAG)) {
            isSummarized = false;
        //error
        } else {
            isSummarized = false;
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(history);
        //request for CommandEntry list
        if (!isSummarized) {
            try {
                HtmlReportGenerator.generateCommandEntryReport(history.getCommandEntryList());
                EventsCenter.getInstance().post(new ShowHistoryRequestEvent(HtmlReportGenerator.DEFAULT_REPORT_NAME));
                return new CommandResult(MESSAGE_HISTORY_WINDOW);
            } catch (IOException e) {
                e.printStackTrace();
                return new CommandResult(MESSAGE_HISTORY_WINDOW_FAILURE);
            }
        }

        //request for command history in the result window
        List<String> previousCommands = history.getHistory();
        if (isSummarized && previousCommands.isEmpty()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        } else if (isSummarized) {
            Collections.reverse(previousCommands);
            return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", previousCommands)));
        //error
        } else {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HistoryCommand // instanceof handles nulls
                && isSummarized == ((HistoryCommand) other).isSummarized); // state check
    }
}
