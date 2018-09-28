package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyThanePark;
import seedu.address.model.ThanePark;

/**
 * Represents a storage for {@link ThanePark}.
 */
public interface ThaneParkStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns ThanePark data as a {@link ReadOnlyThanePark}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyThanePark> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyThanePark> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyThanePark} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyThanePark addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyThanePark)
     */
    void saveAddressBook(ReadOnlyThanePark addressBook, Path filePath) throws IOException;

}
