package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ride.Status;

/**
 * View all opened rides in thane park.
 */
public class QuickViewCommand extends Command {

    public static final String COMMAND_WORD = "quickView";

    public static final String MESSAGE_SUCCESS = "Opened rides listed!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.updateFilteredRideList(ride -> ride.getStatus().equals(Status.OPEN));

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
