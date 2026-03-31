package cpp.logic.parser;

import cpp.logic.Messages;
import cpp.logic.commands.FindAssignmentCommand;
import cpp.logic.parser.exceptions.ParseException;
import cpp.model.assignment.AssignmentDeadlineContainsKeywordPredicate;
import cpp.model.assignment.AssignmentNameContainsKeywordsPredicate;
import cpp.model.assignment.AssignmentSearchPredicate;

/**
 * Parses input arguments and creates a new FindAssignmentCommand object
 */
public class FindAssignmentCommandParser implements Parser<FindAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindAssignmentCommand
     * and returns a FindAssignmentCommand object for execution.
     * 
     * Supports finding by name (default) or deadline (d/)
     * Examples: findass CS2103 project
     * findass d/ 31-12-2024
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindAssignmentCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().replaceAll("\\s+", " ");
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindAssignmentCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(trimmedArgs, CliSyntax.PREFIX_DATETIME);

        AssignmentSearchPredicate predicate;

        if (argMultimap.getValue(CliSyntax.PREFIX_DATETIME).isPresent()) {
            String keyword = argMultimap.getValue(CliSyntax.PREFIX_DATETIME).get();
            predicate = new AssignmentDeadlineContainsKeywordPredicate(keyword);
        } else {
            // Default to name search
            String searchTerms = trimmedArgs.replaceAll("\\s+", " ");
            predicate = new AssignmentNameContainsKeywordsPredicate(searchTerms);
        }

        return new FindAssignmentCommand(predicate);
    }

}
