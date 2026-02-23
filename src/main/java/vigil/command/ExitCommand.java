package vigil.command;

import vigil.storage.Storage;
import vigil.task.TaskList;
import vigil.ui.Ui;

public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        // nothing to do
    }

    @Override
    public boolean isExit() {
        return true;
    }
}