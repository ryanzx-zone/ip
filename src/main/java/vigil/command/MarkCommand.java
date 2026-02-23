package vigil.command;

import vigil.exception.VigilException;
import vigil.storage.Storage;
import vigil.task.TaskList;
import vigil.ui.Ui;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {

    private final String rawIndex;

    public MarkCommand(String rawIndex) {
        this.rawIndex = rawIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws VigilException {
        int taskIndex = tasks.parseTaskIndex(rawIndex);
        tasks.get(taskIndex).markAsDone();
        storage.save(tasks.toArray(), tasks.size());
        ui.showMarkDone(tasks.get(taskIndex));
    }
}