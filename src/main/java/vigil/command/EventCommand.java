package vigil.command;

import vigil.exception.VigilException;
import vigil.storage.Storage;
import vigil.task.Event;
import vigil.task.Task;
import vigil.task.TaskList;
import vigil.ui.Ui;

public class EventCommand extends Command {

    private final String description;
    private final String from;
    private final String to;

    public EventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws VigilException {
        Task task = new Event(description, from, to);
        tasks.add(task);
        storage.save(tasks.toArray(), tasks.size());
        ui.showTaskAdded(task, tasks.size());
    }
}