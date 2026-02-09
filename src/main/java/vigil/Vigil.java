package vigil;

import java.util.Scanner;

import vigil.exception.VigilException;
import vigil.task.Deadline;
import vigil.task.Event;
import vigil.task.Task;
import vigil.task.Todo;

public class Vigil {
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

        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.equalsIgnoreCase("bye")) {
                break;
            }

            printDivider();

            try {
                if (line.equalsIgnoreCase("list")) {
                    System.out.println("Vigil scan complete. Here's your task list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + ". " + tasks[i]);
                    }

                } else if (line.equals("todo") || line.startsWith("todo ")) {
                    String description = line.equals("todo") ? "" : line.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new VigilException("Task entry incomplete. A todo needs a description.");
                    }
                    Task task = new Todo(description);
                    tasks[taskCount] = task;
                    taskCount++;
                    printTaskAdded(task, taskCount);

                } else if (line.equals("mark") || line.startsWith("mark ")) {
                    if (line.equals("mark")) {
                        throw new VigilException("Missing task number. Use: mark <task number>.");
                    }
                    int taskIndex = parseTaskIndex(line.substring(5).trim(), taskCount);
                    tasks[taskIndex].markAsDone();
                    System.out.println("Vigil confirms this task is now complete:");
                    System.out.println("  " + tasks[taskIndex]);

                } else if (line.equals("unmark") || line.startsWith("unmark ")) {
                    if (line.equals("unmark")) {
                        throw new VigilException("Missing task number. Use: unmark <task number>.");
                    }
                    int taskIndex = parseTaskIndex(line.substring(7).trim(), taskCount);
                    tasks[taskIndex].markAsNotDone();
                    System.out.println("Vigil notes this task is no longer complete:");
                    System.out.println("  " + tasks[taskIndex]);

                } else if (line.equals("deadline") || line.startsWith("deadline ")) {
                    String rest = line.equals("deadline") ? "" : line.substring(9).trim();
                    String[] parts = rest.split(" /by ", 2);

                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new VigilException("Deadline entry is invalid. Use: deadline <description> /by <time>");
                    }

                    Task task = new Deadline(parts[0].trim(), parts[1].trim());
                    tasks[taskCount] = task;
                    taskCount++;
                    printTaskAdded(task, taskCount);

                } else if (line.equals("event") || line.startsWith("event ")) {
                    String rest = line.equals("event") ? "" : line.substring(6).trim();
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
                    tasks[taskCount] = task;
                    taskCount++;
                    printTaskAdded(task, taskCount);

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
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new VigilException("Invalid task number. Please enter a positive integer.");
        }

        int taskIndex = taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new VigilException("Task number out of range. Use 1 to " + taskCount + ".");
        }
        return taskIndex;
    }
}



