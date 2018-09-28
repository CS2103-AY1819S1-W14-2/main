package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    public final boolean isSummarized;

    public ShowHelpRequestEvent(boolean summarized) {
        this.isSummarized = summarized;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
