package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAINTENANCE;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ride.RideContainsConditionPredicate;

/**
 * Filters a list of all rides in the thane park which attributes matches the predicate the user inputs.
 * Predicate value must be an integer.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the rides with the predicate that the user "
            + "inputs and displays the result as a list with index numbers\n"
            + "Parameters: [PREFIX] [PREDICATE]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_MAINTENANCE + "< 1000";

    private final RideContainsConditionPredicate predicate;

    public FilterCommand(RideContainsConditionPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredRideList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_RIDES_LISTED_OVERVIEW, model.getFilteredRideList().size()));
    }
}
