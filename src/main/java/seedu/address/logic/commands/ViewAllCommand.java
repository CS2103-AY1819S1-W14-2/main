package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RIDES;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ViewAllCommand extends Command {

    public static final String COMMAND_WORD = "view all";

    public static final String MESSAGE_SUCCESS = "Viewed all rides";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRideList(PREDICATE_SHOW_ALL_RIDES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
