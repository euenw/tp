package cpp.logic.commands;

import java.util.List;
import java.util.Objects;

import cpp.commons.core.index.Index;
import cpp.commons.util.ToStringBuilder;
import cpp.logic.Messages;
import cpp.logic.commands.exceptions.CommandException;
import cpp.model.Model;
import cpp.model.contact.Contact;

/**
 * Deletes a contact identified using it's displayed index from the address
 * book.
 */
public class DeleteContactCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted Contact: %1$s";

    private final Index targetIndex;

    public DeleteContactCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();

        if (this.targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToDelete = lastShownList.get(this.targetIndex.getZeroBased());
        model.deleteContact(contactToDelete);
        return new CommandResult(
                String.format(DeleteContactCommand.MESSAGE_DELETE_CONTACT_SUCCESS, Messages.format(contactToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteContactCommand)) {
            return false;
        }

        DeleteContactCommand otherDeleteContactCommand = (DeleteContactCommand) other;
        return this.targetIndex.equals(otherDeleteContactCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", this.targetIndex)
                .toString();
    }
}
