package seedu.address.model.ride;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class WaitTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new WaitTime(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new WaitTime(invalidEmail));
    }

    @Test
    public void isValidWaitTime() {
        // null waiting time
        Assert.assertThrows(NullPointerException.class, () -> WaitTime.isValidWaitTime(null));

        // blank waiting time
        assertFalse(WaitTime.isValidWaitTime("")); // empty string
        assertFalse(WaitTime.isValidWaitTime(" ")); // spaces only

        // missing parts
        assertFalse(WaitTime.isValidWaitTime("@example.com")); // missing local part
        assertFalse(WaitTime.isValidWaitTime("peterjackexample.com")); // missing '@' symbol
        assertFalse(WaitTime.isValidWaitTime("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(WaitTime.isValidWaitTime("peterjack@-")); // invalid domain name
        assertFalse(WaitTime.isValidWaitTime("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(WaitTime.isValidWaitTime("peter jack@example.com")); // spaces in local part
        assertFalse(WaitTime.isValidWaitTime("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(WaitTime.isValidWaitTime(" peterjack@example.com")); // leading space
        assertFalse(WaitTime.isValidWaitTime("peterjack@example.com ")); // trailing space
        assertFalse(WaitTime.isValidWaitTime("peterjack@@example.com")); // double '@' symbol
        assertFalse(WaitTime.isValidWaitTime("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(WaitTime.isValidWaitTime("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(WaitTime.isValidWaitTime("peterjack@.example.com")); // domain name starts with a period
        assertFalse(WaitTime.isValidWaitTime("peterjack@example.com.")); // domain name ends with a period
        assertFalse(WaitTime.isValidWaitTime("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(WaitTime.isValidWaitTime("peterjack@example.com-")); // domain name ends with a hyphen

        assertFalse(WaitTime.isValidWaitTime("a@bc")); // minimal
        assertFalse(WaitTime.isValidWaitTime("test@localhost")); // alphabets only
        assertFalse(WaitTime.isValidWaitTime("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertFalse(WaitTime.isValidWaitTime("123@145")); // numeric local part and domain name
        assertFalse(WaitTime.isValidWaitTime("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertFalse(WaitTime.isValidWaitTime("peter_jack@very-very-very-long-example.com")); // long domain name
        assertFalse(WaitTime.isValidWaitTime("if.you.dream.it_you.can.do.it@example.com")); // long local part

        // valid waiting time
        assertTrue(WaitTime.isValidWaitTime("17"));
    }
}
