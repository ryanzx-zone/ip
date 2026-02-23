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

/**
 * Represents a command to list all deadlines and events occurring on a specific date.
 * Deadlines are matched by their due date, and events are matched if the date falls
 * within their start-to-end range.
 */
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

            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (date.equals(deadline.getByDate())) {
                    matchingTasks.add(task);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (isOnDate(event)) {
                    matchingTasks.add(task);
                }
            }
        }

        ui.showTasksOnDate(matchingTasks, date);
    }

    /**
     * Checks whether the target date falls within the event's date range.
     * If only one boundary is parseable, checks for an exact match on that date.
     * Returns false if neither boundary could be parsed.
     */
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