package vigil.command;

import vigil.exception.VigilException;
import vigil.storage.Storage;
import vigil.task.TaskList;
import vigil.task.Todo;
import vigil.task.Task;
import vigil.ui.Ui;

public class TodoCommand extends Command {

    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws VigilException {
        Task task = new Todo(description);
        tasks.add(task);
        storage.save(tasks.toArray(), tasks.size());
        ui.showTaskAdded(task, tasks.size());
    }
}