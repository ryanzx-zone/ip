package vigil.command;

import vigil.storage.Storage;
import vigil.task.TaskList;
import vigil.ui.Ui;

/**
 * Command that signals the application to exit.
 */
public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        // No action required.
    }

    @Override
    public boolean isExit() {
        return true;
    }
}