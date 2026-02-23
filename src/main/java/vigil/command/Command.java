package vigil.command;

import vigil.exception.VigilException;
import vigil.storage.Storage;
import vigil.task.TaskList;
import vigil.ui.Ui;

public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws VigilException;

    public boolean isExit() {
        return false;
    }
}