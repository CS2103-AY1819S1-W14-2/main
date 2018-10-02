package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAINTENANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAITING_TIME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ride.Ride;

/**
 * Adds a ride to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a ride to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_MAINTENANCE + "MAINTENANCE "
            + PREFIX_WAITING_TIME + "WAITING TIME "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Transformers "
            + PREFIX_MAINTENANCE + "3 "
            + PREFIX_WAITING_TIME + "45 "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "4D "
            + PREFIX_TAG + "indoor";

    public static final String MESSAGE_SUCCESS = "New ride added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This ride already exists in the address book";

    private final Ride toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Ride}
     */
    public AddCommand(Ride ride) {
        requireNonNull(ride);
        toAdd = ride;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
