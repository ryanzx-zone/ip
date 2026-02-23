package vigil.command;

import java.time.LocalDate;
import java.util.ArrayList;

import vigil.exception.VigilException;
import vigil.storage.Storage;
import vigil.task.Deadline;
import vigil.task.Event;
import vigil.task.Task;
import vigil.task.TaskList;
import vigil.ui.Ui;

public class ScheduleCommand extends Command {

    private final LocalDate date;

    public ScheduleCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws VigilException {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            if (task instanceof Deadline deadline) {
                if (date.equals(deadline.getByDate())) {
                    matchingTasks.add(task);
                }
            } else if (task instanceof Event event) {
                if (isOnDate(event)) {
                    matchingTasks.add(task);
                }
            }
        }

        ui.showTasksOnDate(matchingTasks, date);
    }

    private boolean isOnDate(Event event) {
        LocalDate from = event.getFromDate();
        LocalDate to = event.getToDate();

        if (from != null && to != null) {
            return !date.isBefore(from) && !date.isAfter(to);
        }
        if (from != null) {
            return date.equals(from);
        }
        if (to != null) {
            return date.equals(to);
        }
        return false;
    }
}