package cpp.logic.parser;

import java.util.Arrays;

import cpp.logic.Messages;
import cpp.logic.commands.FindContactCommand;
import cpp.logic.parser.exceptions.ParseException;
import cpp.model.contact.ContactEmailContainsKeywordsPredicate;
import cpp.model.contact.ContactNameContainsKeywordsPredicate;
import cpp.model.contact.ContactPhoneContainsKeywordsPredicate;
import cpp.model.contact.ContactSearchPredicate;

/**
 * Parses input arguments and creates a new FindContactCommand object
 */
public class FindContactCommandParser implements Parser<FindContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindContactCommand
     * and returns a FindContactCommand object for execution.
     * 
     * Supports finding by name (default), phone (p/), or email (e/)
     * Examples: findcontact alice
     * findcontact p/ 91234567
     * findcontact e/ gmail
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindContactCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindContactCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(trimmedArgs, CliSyntax.PREFIX_NAME,
                CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_EMAIL);

        ContactSearchPredicate predicate;

        if (argMultimap.getValue(CliSyntax.PREFIX_PHONE).isPresent()) {
            String[] phoneKeywords = argMultimap.getValue(CliSyntax.PREFIX_PHONE).get().split("\\s+");
            predicate = new ContactPhoneContainsKeywordsPredicate(Arrays.asList(phoneKeywords));
        } else if (argMultimap.getValue(CliSyntax.PREFIX_EMAIL).isPresent()) {
            String[] emailKeywords = argMultimap.getValue(CliSyntax.PREFIX_EMAIL).get().split("\\s+");
            predicate = new ContactEmailContainsKeywordsPredicate(Arrays.asList(emailKeywords));
        } else if (argMultimap.getValue(CliSyntax.PREFIX_NAME).isPresent()) {
            String[] nameKeywords = argMultimap.getValue(CliSyntax.PREFIX_NAME).get().split("\\s+");
            predicate = new ContactNameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        } else {
            // Default to name search if no prefix is provided
            String[] nameKeywords = trimmedArgs.split("\\s+");
            predicate = new ContactNameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        }

        return new FindContactCommand(predicate);
    }

}
