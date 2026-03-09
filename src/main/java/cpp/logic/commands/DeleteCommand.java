package cpp.logic.commands;

/**
 * Represents a delete command with hidden internal logic and the ability to be executed.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a contact or assignment identified by the index number used in the displayed list.\n"
            + "Parameters: ct/INDEX or ass/INDEX (must be a positive integer)\n"
            + "Examples: " + COMMAND_WORD + " ct/1, " + COMMAND_WORD + " ass/1";

}
