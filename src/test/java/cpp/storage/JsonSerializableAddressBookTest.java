package cpp.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cpp.commons.exceptions.IllegalValueException;
import cpp.commons.util.JsonUtil;
import cpp.model.AddressBook;
import cpp.testutil.Assert;
import cpp.testutil.TypicalAssignments;
import cpp.testutil.TypicalPersons;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = JsonSerializableAddressBookTest.TEST_DATA_FOLDER
            .resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = JsonSerializableAddressBookTest.TEST_DATA_FOLDER
            .resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = JsonSerializableAddressBookTest.TEST_DATA_FOLDER
            .resolve("duplicatePersonAddressBook.json");
    private static final Path TYPICAL_ASSIGNMENTS_FILE = JsonSerializableAddressBookTest.TEST_DATA_FOLDER
            .resolve("typicalAssignmentsAddressBook.json");
    private static final Path INVALID_ASSIGNMENT_FILE = JsonSerializableAddressBookTest.TEST_DATA_FOLDER
            .resolve("invalidAssignmentAddressBook.json");
    private static final Path DUPLICATE_ASSIGNMENT_FILE = JsonSerializableAddressBookTest.TEST_DATA_FOLDER
            .resolve("duplicateAssignmentAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil
                .readJsonFile(JsonSerializableAddressBookTest.TYPICAL_PERSONS_FILE,
                        JsonSerializableAddressBook.class)
                .get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        Assertions.assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil
                .readJsonFile(JsonSerializableAddressBookTest.INVALID_PERSON_FILE,
                        JsonSerializableAddressBook.class)
                .get();
        Assert.assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil
                .readJsonFile(JsonSerializableAddressBookTest.DUPLICATE_PERSON_FILE,
                        JsonSerializableAddressBook.class)
                .get();
        Assert.assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalAssignmentsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil
                .readJsonFile(JsonSerializableAddressBookTest.TYPICAL_ASSIGNMENTS_FILE,
                        JsonSerializableAddressBook.class)
                .get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook expected = new AddressBook();
        expected.addAssignment(TypicalAssignments.ASSIGNMENT_ONE);
        Assertions.assertEquals(addressBookFromFile, expected);
    }

    @Test
    public void toModelType_invalidAssignmentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil
                .readJsonFile(JsonSerializableAddressBookTest.INVALID_ASSIGNMENT_FILE,
                        JsonSerializableAddressBook.class)
                .get();
        Assert.assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateAssignments_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil
                .readJsonFile(JsonSerializableAddressBookTest.DUPLICATE_ASSIGNMENT_FILE,
                        JsonSerializableAddressBook.class)
                .get();
        Assert.assertThrows(IllegalValueException.class,
                JsonSerializableAddressBook.MESSAGE_DUPLICATE_ASSIGNMENT,
                dataFromFile::toModelType);
    }

}
