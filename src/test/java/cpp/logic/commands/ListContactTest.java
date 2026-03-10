package cpp.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpp.model.Model;
import cpp.model.ModelManager;
import cpp.model.UserPrefs;
import cpp.testutil.TypicalContacts;
import cpp.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * ListContactCommand.
 */
public class ListContactTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        this.model = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());
        this.expectedModel = new ModelManager(this.model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandResult expectedResult = new CommandResult(ListCommand.MESSAGE_SUCCESS,
                CommandResult.ListView.CONTACTS);
        CommandTestUtil.assertCommandSuccess(new ListContactCommand(), this.model, expectedResult,
                this.expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        CommandTestUtil.showContactAtIndex(this.model, TypicalIndexes.INDEX_FIRST_CONTACT);
        CommandResult expectedResult = new CommandResult(ListCommand.MESSAGE_SUCCESS,
                CommandResult.ListView.CONTACTS);
        CommandTestUtil.assertCommandSuccess(new ListContactCommand(), this.model, expectedResult,
                this.expectedModel);
    }
}
