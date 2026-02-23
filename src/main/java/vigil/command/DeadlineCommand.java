package vigil.command;

import vigil.exception.VigilException;
import vigil.storage.Storage;
import vigil.task.Deadline;
import vigil.task.Task;
import vigil.task.TaskList;
import vigil.ui.Ui;

/**
 * Represents a command to add a new deadline task.
 */
public class DeadlineCommand extends Command {

    private final String description;
    private final String by;

    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws VigilException {
        Task task = new Deadline(description, by);
        tasks.add(task);
        storage.save(tasks.toArray(), tasks.size());
        ui.showTaskAdded(task, tasks.size());
    }
}