package cpp.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpp.model.Model;
import cpp.model.ModelManager;
import cpp.model.UserPrefs;
import cpp.testutil.TypicalContacts;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * ListClassCommand.
 */
public class ListClassTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        this.model = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());
        this.expectedModel = new ModelManager(this.model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listClasses_returnsClassesMessage() {
        CommandResult expectedResult = new CommandResult(ListCommand.MESSAGE_CLASSES,
                CommandResult.ListView.CLASSGROUPS);
        CommandTestUtil.assertCommandSuccess(new ListClassCommand(), this.model, expectedResult,
                this.expectedModel);
    }
}
