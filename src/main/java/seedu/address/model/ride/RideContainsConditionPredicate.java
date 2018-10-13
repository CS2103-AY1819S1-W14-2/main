package seedu.address.model.ride;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Ride}'s attributes matches the predicate given.
 */
public class RideContainsConditionPredicate implements Predicate<Ride> {
    private final List<AttributePredicate> attributePredicates;

    public RideContainsConditionPredicate(List<AttributePredicate> predicates) {
        attributePredicates = predicates;
    }

    @Override
    public boolean test(Ride ride) {
        return attributePredicates.stream().allMatch(p -> {
            NumericAttribute attributeToTest = p.getAttribute();
            NumericAttribute rideAttributeToTest = ride.getAttribute(attributeToTest);
            return p.test(rideAttributeToTest);
        });
    }
}
