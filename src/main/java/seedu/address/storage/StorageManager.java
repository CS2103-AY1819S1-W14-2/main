package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ThaneParkChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyThanePark;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of ThanePark data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ThaneParkStorage thaneParkStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ThaneParkStorage thaneParkStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.thaneParkStorage = thaneParkStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ ThanePark methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return thaneParkStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyThanePark> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(thaneParkStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyThanePark> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return thaneParkStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyThanePark addressBook) throws IOException {
        saveAddressBook(addressBook, thaneParkStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyThanePark addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        thaneParkStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(ThaneParkChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
