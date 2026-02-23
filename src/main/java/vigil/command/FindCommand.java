package vigil.command;

import java.util.ArrayList;

import vigil.exception.VigilException;
import vigil.storage.Storage;
import vigil.task.Task;
import vigil.task.TaskList;
import vigil.ui.Ui;

public class FindCommand extends Command {

    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws VigilException {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        ui.showFindResults(matchingTasks, keyword);
    }
}