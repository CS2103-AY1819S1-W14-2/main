package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.util.FilePathToUrl;
import seedu.address.commons.util.RidePageGenerator;
import seedu.address.model.ride.Ride;
import seedu.address.ui.exceptions.BrowserRelatedUiPart;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends BrowserRelatedUiPart<Region> {

    public static final FilePathToUrl RIDE_PAGE_PATH =
        new FilePathToUrl("src/main/resources/docs/ride.html");
    private static final String RIDE_PAGE_TITLE = "Ride information";
    private static final String TEXT_REPLACEMENT_JAVASCRIPT =
        "function updateRide(listOfFields) {" +
            "for (index in listOfFields) {" +
                "document.getElementById(listOfFields[index][0]).innerHTML = listOfFields[index][1];" +
            "}" +
        "}";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        registerAsAnEventHandler(this);
        try {
            RidePageGenerator.getInstance().generateHtml(RIDE_PAGE_TITLE, Ride.RIDE_TEMPLATE, RIDE_PAGE_PATH);
            loadDefaultPage();
        } catch (IOException ie) {
            ie.printStackTrace();
            logger.warning("Unable to create ride page.");
        }
    }

    @Override
    protected WebView getWebView() {
        return browser;
    }

    private void loadRidePage(Ride ride) {
        loadPage(RIDE_PAGE_PATH);
        int totalFields = ride.getFieldHeaders().size();
        StringBuilder parameters = new StringBuilder();
        parameters.append(String.format("[\"%1s\", \"%2s\"], ", "name", ride.getName().fullName));
        for (int i = 0; i < totalFields; i++) {
            parameters.append(String.format("[\"%1s\", \"%2s\"], ",
                ride.getFieldHeaders().get(i), ride.getFields().get(i)));
        }
        queuedJavascript.add(TEXT_REPLACEMENT_JAVASCRIPT);
        queuedJavascript.add(String.format("updateRide([%1s])", parameters.toString()));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    public void loadDefaultPage() {
        loadPage(HelpWindow.SHORT_HELP_FILE_PATH);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadRidePage(event.getNewSelection());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }
}
