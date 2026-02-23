package vigil.command;

import vigil.exception.VigilException;
import vigil.storage.Storage;
import vigil.task.Task;
import vigil.task.TaskList;
import vigil.ui.Ui;

/**
 * Represents a command to delete a task by its displayed index.
 */
public class DeleteCommand extends Command {

    private final String rawIndex;

    public DeleteCommand(String rawIndex) {
        this.rawIndex = rawIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws VigilException {
        int taskIndex = tasks.parseTaskIndex(rawIndex);
        Task removedTask = tasks.delete(taskIndex);
        storage.save(tasks.toArray(), tasks.size());
        ui.showTaskDeleted(removedTask, tasks.size());
    }
}