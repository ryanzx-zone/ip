package vigil;

import vigil.exception.VigilException;
import vigil.storage.Storage;
import vigil.task.Deadline;
import vigil.task.Event;
import vigil.task.Task;
import vigil.task.TaskList;
import vigil.task.Todo;
import vigil.ui.Ui;

public class Vigil {

    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_BYE = "bye";

    private static final String DATA_FOLDER = "data";
    private static final String DATA_FILE = "vigil.txt";

    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage(DATA_FOLDER, DATA_FILE);

        ui.showWelcome();

        TaskList tasks;
        try {
            tasks = new TaskList(storage.load());
        } catch (VigilException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }

        while (ui.hasNextLine()) {
            String line = ui.readCommand();

            if (line.equalsIgnoreCase(COMMAND_BYE)) {
                break;
            }

            ui.showDivider();

            try {
                if (line.equalsIgnoreCase(COMMAND_LIST)) {
                    ui.showTaskList(tasks);

                } else if (line.equals(COMMAND_TODO) || line.startsWith(COMMAND_TODO + " ")) {
                    String description = line.equals(COMMAND_TODO)
                            ? ""
                            : line.substring(COMMAND_TODO.length()).trim();
                    if (description.isEmpty()) {
                        throw new VigilException("Task entry incomplete. A todo needs a description.");
                    }

                    Task task = new Todo(description);
                    tasks.add(task);
                    saveTasks(storage, tasks);
                    ui.showTaskAdded(task, tasks.size());

                } else if (line.equals(COMMAND_MARK) || line.startsWith(COMMAND_MARK + " ")) {
                    if (line.equals(COMMAND_MARK)) {
                        throw new VigilException("Missing task number. Use: mark <task number>.");
                    }

                    int taskIndex = tasks.parseTaskIndex(line.substring(COMMAND_MARK.length()).trim());
                    tasks.get(taskIndex).markAsDone();
                    saveTasks(storage, tasks);
                    ui.showMarkDone(tasks.get(taskIndex));

                } else if (line.equals(COMMAND_UNMARK) || line.startsWith(COMMAND_UNMARK + " ")) {
                    if (line.equals(COMMAND_UNMARK)) {
                        throw new VigilException("Missing task number. Use: unmark <task number>.");
                    }

                    int taskIndex = tasks.parseTaskIndex(line.substring(COMMAND_UNMARK.length()).trim());
                    tasks.get(taskIndex).markAsNotDone();
                    saveTasks(storage, tasks);
                    ui.showUnmarkDone(tasks.get(taskIndex));

                } else if (line.equals(COMMAND_DEADLINE) || line.startsWith(COMMAND_DEADLINE + " ")) {
                    String rest = line.equals(COMMAND_DEADLINE)
                            ? ""
                            : line.substring(COMMAND_DEADLINE.length()).trim();
                    String[] parts = rest.split(" /by ", 2);

                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new VigilException("Deadline entry is invalid. Use: deadline <description> /by <time>");
                    }

                    Task task = new Deadline(parts[0].trim(), parts[1].trim());
                    tasks.add(task);
                    saveTasks(storage, tasks);
                    ui.showTaskAdded(task, tasks.size());

                } else if (line.equals(COMMAND_EVENT) || line.startsWith(COMMAND_EVENT + " ")) {
                    String rest = line.equals(COMMAND_EVENT)
                            ? ""
                            : line.substring(COMMAND_EVENT.length()).trim();
                    String[] firstSplit = rest.split(" /from ", 2);

                    if (firstSplit.length < 2 || firstSplit[0].trim().isEmpty()) {
                        throw new VigilException("Event entry is invalid. Use: event <desc> /from <start> /to <end>");
                    }

                    String desc = firstSplit[0].trim();
                    String[] secondSplit = firstSplit[1].split(" /to ", 2);

                    if (secondSplit.length < 2 || secondSplit[0].trim().isEmpty() || secondSplit[1].trim().isEmpty()) {
                        throw new VigilException("Event entry is invalid. Use: event <desc> /from <start> /to <end>");
                    }

                    Task task = new Event(desc, secondSplit[0].trim(), secondSplit[1].trim());
                    tasks.add(task);
                    saveTasks(storage, tasks);
                    ui.showTaskAdded(task, tasks.size());

                } else if (line.equals(COMMAND_DELETE) || line.startsWith(COMMAND_DELETE + " ")) {
                    if (line.equals(COMMAND_DELETE)) {
                        throw new VigilException("Missing task number. Use: delete <task number>.");
                    }

                    int taskIndex = tasks.parseTaskIndex(line.substring(COMMAND_DELETE.length()).trim());
                    Task removedTask = tasks.delete(taskIndex);
                    saveTasks(storage, tasks);
                    ui.showTaskDeleted(removedTask, tasks.size());

                } else {
                    throw new VigilException("Command not recognised.");
                }
            } catch (VigilException e) {
                ui.showError(e.getMessage());
            }

            ui.showDivider();
        }

        ui.showGoodbye();
    }

    private static void saveTasks(Storage storage, TaskList tasks) throws VigilException {
        storage.save(tasks.toArray(), tasks.size());
    }
}