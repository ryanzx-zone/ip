package vigil;

import java.util.ArrayList;
import java.util.Scanner;

import vigil.exception.VigilException;
import vigil.task.Deadline;
import vigil.task.Event;
import vigil.task.Task;
import vigil.task.Todo;

public class Vigil {

    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_BYE = "bye";

    public static void main(String[] args) {
        String logo = """
                ██╗   ██╗██╗ ██████╗ ██╗██╗
                ██║   ██║██║██╔════╝ ██║██║
                ██║   ██║██║██║  ███╗██║██║
                ╚██╗ ██╔╝██║██║   ██║██║██║
                 ╚████╔╝ ██║╚██████╔╝██║███████╗
                  ╚═══╝  ╚═╝ ╚═════╝ ╚═╝╚══════╝
                """;


        System.out.println(logo);
        printDivider();
        System.out.println("Hello! Vigil is online.");
        System.out.println("What can I do for you today?");
        printDivider();

        Scanner scanner = new Scanner(System.in);

        ArrayList<Task> tasks = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase(COMMAND_BYE)) {
                break;
            }

            printDivider();

            try {
                if (line.equalsIgnoreCase(COMMAND_LIST)) {
                    System.out.println("Vigil scan complete. Here's your task list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }

                } else if (line.equals(COMMAND_TODO) || line.startsWith(COMMAND_TODO + " ")) {
                    String description = line.equals(COMMAND_TODO)
                            ? ""
                            : line.substring(COMMAND_TODO.length()).trim();
                    if (description.isEmpty()) {
                        throw new VigilException("Task entry incomplete. A todo needs a description.");
                    }
                    Task task = new Todo(description);
                    tasks.add(task);
                    printTaskAdded(task, tasks.size());

                } else if (line.equals(COMMAND_MARK) || line.startsWith(COMMAND_MARK + " ")) {
                    if (line.equals(COMMAND_MARK)) {
                        throw new VigilException("Missing task number. Use: mark <task number>.");
                    }
                    int taskIndex = parseTaskIndex(line.substring(COMMAND_MARK.length()).trim(), tasks.size());
                    tasks.get(taskIndex).markAsDone();
                    System.out.println("Vigil confirms this task is now complete:");
                    System.out.println("  " + tasks.get(taskIndex));

                } else if (line.equals(COMMAND_UNMARK) || line.startsWith(COMMAND_UNMARK + " ")) {
                    if (line.equals(COMMAND_UNMARK)) {
                        throw new VigilException("Missing task number. Use: unmark <task number>.");
                    }
                    int taskIndex = parseTaskIndex(line.substring(COMMAND_UNMARK.length()).trim(), tasks.size());
                    tasks.get(taskIndex).markAsNotDone();
                    System.out.println("Vigil notes this task is no longer complete:");
                    System.out.println("  " + tasks.get(taskIndex));

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
                    printTaskAdded(task, tasks.size());

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
                    printTaskAdded(task, tasks.size());

                } else if (line.equals(COMMAND_DELETE) || line.startsWith(COMMAND_DELETE + " ")) {
                    if (line.equals(COMMAND_DELETE)) {
                        throw new VigilException("Missing task number. Use: delete <task number>.");
                    }

                    int taskIndex = parseTaskIndex(line.substring(COMMAND_DELETE.length()).trim(), tasks.size());
                    Task removedTask = tasks.remove(taskIndex);

                    System.out.println("Vigil confirms termination. Task removed:");
                    System.out.println("  " + removedTask);
                    System.out.println(tasks.size() + " tasks currently under Vigil's watch.");

                } else {
                    throw new VigilException("Command not recognised.");
                }
            } catch (VigilException e) {
                System.out.println("Vigil alert: " + e.getMessage());
            }
            printDivider();

        }
        printDivider();
        System.out.println("Goodbye. Hope to see you again soon! Vigil is going offline.");
        printDivider();
    }

    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println("Vigil acknowledges. Task successfully recorded:");
        System.out.println("  " + task);
        System.out.println(taskCount + " tasks currently under Vigil's watch.");
    }

    private static void printDivider() {
        System.out.println("_______________________________________________________________________________");
    }

    private static int parseTaskIndex(String raw, int taskCount) throws VigilException {
        if (taskCount == 0) {
            throw new VigilException("No tasks found. Add a task first.");
        }

        int taskNumber;
        try {
            taskNumber = Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new VigilException("Invalid task number.");
        }

        int taskIndex = taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new VigilException("Task number out of range. Use 1 to " + taskCount + ".");
        }
        return taskIndex;
    }
}



