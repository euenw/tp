package cpp.logic.parser.assignment;

import java.util.List;

import cpp.commons.core.index.Index;
import cpp.logic.Messages;
import cpp.logic.commands.assignment.UngradeAssignmentCommand;
import cpp.logic.parser.ArgumentMultimap;
import cpp.logic.parser.ArgumentTokenizer;
import cpp.logic.parser.CliSyntax;
import cpp.logic.parser.Parser;
import cpp.logic.parser.ParserUtil;
import cpp.logic.parser.exceptions.ParseException;
import cpp.model.assignment.AssignmentName;

/**
 * Parses input arguments and creates a new {@code UngradeAssignmentCommand}
 * object
 */
public class UngradeAssignmentCommandParser implements Parser<UngradeAssignmentCommand> {

    /**
     * Parses the given {@code String} and returns an
     * {@code UngradeAssignmentCommand} object for execution.
     */
    @Override
    public UngradeAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_ASSIGNMENT, CliSyntax.PREFIX_CONTACT);

        boolean hasAssignment = argMultimap.getValue(CliSyntax.PREFIX_ASSIGNMENT).isPresent();
        boolean hasContact = argMultimap.getValue(CliSyntax.PREFIX_CONTACT).isPresent();

        if (!hasAssignment || !hasContact || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    UngradeAssignmentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(CliSyntax.PREFIX_ASSIGNMENT, CliSyntax.PREFIX_CONTACT);

        AssignmentName assignmentName = ParserUtil
                .parseAssignmentName(argMultimap.getValue(CliSyntax.PREFIX_ASSIGNMENT).get());

        List<Index> contactIndices = List.of();
        String contactString = argMultimap.getValue(CliSyntax.PREFIX_CONTACT).orElse("");
        contactIndices = ParserUtil.parseContactIndices(contactString);

        return new UngradeAssignmentCommand(assignmentName, contactIndices);
    }

}
