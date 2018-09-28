package seedu.address.model.ride;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MaintenanceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Maintenance(null));
    }

    @Test
    public void constructor_invalidMaintenance_throwsIllegalArgumentException() {
        String invalidMaintenance = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Maintenance(invalidMaintenance));
    }

    @Test
    public void isValidMaintenance() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Maintenance.isValidMaintenance(null));

        // invalid phone numbers
        assertFalse(Maintenance.isValidMaintenance("")); // empty string
        assertFalse(Maintenance.isValidMaintenance(" ")); // spaces only
        assertFalse(Maintenance.isValidMaintenance("phone")); // non-numeric
        assertFalse(Maintenance.isValidMaintenance("9011p041")); // alphabets within digits
        assertFalse(Maintenance.isValidMaintenance("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Maintenance.isValidMaintenance("1")); // exactly 1 numbers
        assertTrue(Maintenance.isValidMaintenance("93121534"));
        assertTrue(Maintenance.isValidMaintenance("124293842033123")); // long phone numbers
    }
}
