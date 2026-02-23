package vigil.command;

import vigil.exception.VigilException;
import vigil.storage.Storage;
import vigil.task.TaskList;
import vigil.ui.Ui;

public class UnmarkCommand extends Command {

    private final String rawIndex;

    public UnmarkCommand(String rawIndex) {
        this.rawIndex = rawIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws VigilException {
        int taskIndex = tasks.parseTaskIndex(rawIndex);
        tasks.get(taskIndex).markAsNotDone();
        storage.save(tasks.toArray(), tasks.size());
        ui.showUnmarkDone(tasks.get(taskIndex));
    }
}