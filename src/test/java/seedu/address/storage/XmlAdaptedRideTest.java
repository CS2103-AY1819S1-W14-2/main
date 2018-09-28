package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedRide.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ride.Address;
import seedu.address.model.ride.Maintenance;
import seedu.address.model.ride.Name;
import seedu.address.model.ride.WaitTime;
import seedu.address.testutil.Assert;

public class XmlAdaptedRideTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_MAINTENANCE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_MAINTENANCE = BENSON.getDaysSinceMaintenance().toString();
    private static final String VALID_WAIT_TIME = BENSON.getWaitingTime().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedRide person = new XmlAdaptedRide(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedRide person =
                new XmlAdaptedRide(INVALID_NAME, VALID_MAINTENANCE, VALID_WAIT_TIME, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedRide person = new XmlAdaptedRide(null,
                VALID_MAINTENANCE, VALID_WAIT_TIME, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidMaintenance_throwsIllegalValueException() {
        XmlAdaptedRide person =
                new XmlAdaptedRide(VALID_NAME, INVALID_MAINTENANCE, VALID_WAIT_TIME, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Maintenance.MESSAGE_MAINTENANCE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullMaintenance_throwsIllegalValueException() {
        XmlAdaptedRide person = new XmlAdaptedRide(VALID_NAME, null, VALID_WAIT_TIME, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Maintenance.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedRide person =
                new XmlAdaptedRide(VALID_NAME, VALID_MAINTENANCE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = WaitTime.MESSAGE_WAIT_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedRide person = new XmlAdaptedRide(VALID_NAME, VALID_MAINTENANCE, null, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, WaitTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedRide person =
                new XmlAdaptedRide(VALID_NAME, VALID_MAINTENANCE, VALID_WAIT_TIME, INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedRide person = new XmlAdaptedRide(VALID_NAME,
                VALID_MAINTENANCE, VALID_WAIT_TIME, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedRide person =
                new XmlAdaptedRide(VALID_NAME, VALID_MAINTENANCE, VALID_WAIT_TIME, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
