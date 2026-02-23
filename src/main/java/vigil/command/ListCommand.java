package vigil.command;

import vigil.storage.Storage;
import vigil.task.TaskList;
import vigil.ui.Ui;

public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }
}