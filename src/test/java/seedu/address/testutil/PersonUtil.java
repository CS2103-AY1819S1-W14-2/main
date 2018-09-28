package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAINTENANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAITING_TIME;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.ride.Ride;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Ride.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code ride}.
     */
    public static String getAddCommand(Ride ride) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(ride);
    }

    /**
     * Returns the part of command string for the given {@code ride}'s details.
     */
    public static String getPersonDetails(Ride ride) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + ride.getName().fullName + " ");
        sb.append(PREFIX_MAINTENANCE + ride.getDaysSinceMaintenance().toString() + " ");
        sb.append(PREFIX_WAITING_TIME + ride.getWaitingTime().toString() + " ");
        sb.append(PREFIX_ADDRESS + ride.getAddress().value + " ");
        ride.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getMaintenance().ifPresent(
            phone -> sb.append(PREFIX_MAINTENANCE).append(phone.getValue()).append(" "));
        descriptor.getWaitTime().ifPresent(
            email-> sb.append(PREFIX_WAITING_TIME).append(email.getValue()).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
