package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyThanePark;

/** Indicates the ThanePark in the model has changed*/
public class ThaneParkChangedEvent extends BaseEvent {

    public final ReadOnlyThanePark data;

    public ThaneParkChangedEvent(ReadOnlyThanePark data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getRideList().size();
    }
}
