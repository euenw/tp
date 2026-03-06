package address.logic.commands;

import org.junit.jupiter.api.Test;

import address.model.Model;
import address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(HelpCommand.SHOWING_HELP_MESSAGE, true, false);
        CommandTestUtil.assertCommandSuccess(new HelpCommand(), this.model, expectedCommandResult, this.expectedModel);
    }
}
