package cpp.logic.commands;

/**
 * Represents a delete command with hidden internal logic and the ability to be executed.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes contacts identified by indices, or an assignment identified by name.\n"
            + "Parameters: ct/INDEX [MORE_INDICES]... (must be positive integers) or ass/ASSIGNMENT_NAME\n"
            + "Examples: " + COMMAND_WORD + " ct/1 2 3, " + COMMAND_WORD + " ass/Assignment 1";

}
