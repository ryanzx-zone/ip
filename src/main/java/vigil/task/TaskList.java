package vigil.task;

import java.util.ArrayList;

import vigil.exception.VigilException;

public class TaskList {

    private static final int MAX_TASKS = 100;

    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(Task[] loadedTasks) {
        this.tasks = new ArrayList<>();
        for (Task task : loadedTasks) {
            if (tasks.size() >= MAX_TASKS) {
                break;
            }
            tasks.add(task);
        }
    }

    public void add(Task task) throws VigilException {
        if (tasks.size() >= MAX_TASKS) {
            throw new VigilException("Task limit reached. Cannot add more than " + MAX_TASKS + " tasks.");
        }
        tasks.add(task);
    }

    public Task delete(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public Task[] toArray() {
        return tasks.toArray(new Task[0]);
    }

    public int parseTaskIndex(String raw) throws VigilException {
        if (tasks.isEmpty()) {
            throw new VigilException("No tasks found. Add a task first.");
        }

        int taskNumber;
        try {
            taskNumber = Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new VigilException("Invalid task number.");
        }

        int taskIndex = taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new VigilException("Task number out of range. Use 1 to " + tasks.size() + ".");
        }
        return taskIndex;
    }
}