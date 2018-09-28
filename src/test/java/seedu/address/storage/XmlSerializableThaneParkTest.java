package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.ThanePark;
import seedu.address.testutil.TypicalPersons;

public class XmlSerializableThaneParkTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableThaneParkTest");
    private static final Path TYPICAL_RIDES_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.xml");
    private static final Path INVALID_RIDE_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.xml");
    private static final Path DUPLICATE_RIDE_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableThanePark dataFromFile = XmlUtil.getDataFromFile(TYPICAL_RIDES_FILE,
                XmlSerializableThanePark.class);
        ThanePark addressBookFromFile = dataFromFile.toModelType();
        ThanePark typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableThanePark dataFromFile = XmlUtil.getDataFromFile(INVALID_RIDE_FILE,
                XmlSerializableThanePark.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableThanePark dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_RIDE_FILE,
                XmlSerializableThanePark.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableThanePark.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
