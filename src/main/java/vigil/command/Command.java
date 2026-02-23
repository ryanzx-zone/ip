package vigil.command;

import vigil.exception.VigilException;
import vigil.storage.Storage;
import vigil.task.TaskList;
import vigil.ui.Ui;

/**
 * Represents an abstract command that can be executed by the Vigil chatbot.
 * Each subclass encapsulates a specific user action such as adding or
 * deleting tasks.
 */
public abstract class Command {

    /**
     * Executes this command with the given task list, UI, and storage.
     *
     * @param tasks The task list to operate on.
     * @param ui The UI to display results to the user.
     * @param storage The storage to persist changes.
     * @throws VigilException If the command encounters an error during execution.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws VigilException;

    public boolean isExit() {
        return false;
    }
}