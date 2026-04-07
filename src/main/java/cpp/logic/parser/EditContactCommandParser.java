package cpp.logic.parser;

import java.util.Objects;

import cpp.commons.core.index.Index;
import cpp.logic.Messages;
import cpp.logic.commands.EditContactCommand;
import cpp.logic.commands.EditContactCommand.EditContactDescriptor;
import cpp.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditContactCommand object
 */
public class EditContactCommandParser implements Parser<EditContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand
     * and returns an EditContactCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditContactCommand parse(String args) throws ParseException {
        Objects.requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE,
                CliSyntax.PREFIX_EMAIL,
                CliSyntax.PREFIX_ADDRESS, CliSyntax.PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE, CliSyntax.PREFIX_EMAIL,
                CliSyntax.PREFIX_ADDRESS, CliSyntax.PREFIX_TAG);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    EditContactCommand.MESSAGE_USAGE));
        }

        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();

        if (argMultimap.getValue(CliSyntax.PREFIX_NAME).isPresent()) {
            editContactDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_PHONE).isPresent()) {
            editContactDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(CliSyntax.PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_EMAIL).isPresent()) {
            editContactDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(CliSyntax.PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_ADDRESS).isPresent()) {
            editContactDescriptor
                    .setAddress(ParserUtil.parseAddress(argMultimap.getValue(CliSyntax.PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_TAG).isPresent()) {
            String tagsValue = argMultimap.getValue(CliSyntax.PREFIX_TAG).orElse("");
            editContactDescriptor.setTags(ParserUtil.parseTags(tagsValue));
        }

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditContactCommand.MESSAGE_NOT_EDITED);
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        return new EditContactCommand(index, editContactDescriptor);
    }
}
