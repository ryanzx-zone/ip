import java.util.Scanner;

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

        while (true) {
            String line = scanner.nextLine();

            if (line.equalsIgnoreCase("bye")) {
                break;
            }

            printDivider();

            if (line.equalsIgnoreCase("list")) {
                System.out.println("Vigil scan complete. Here’s your task list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
            } else if (line.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(line.substring(5).trim());
                int taskIndex = taskNumber - 1;
                tasks[taskIndex].markAsDone();
                System.out.println("Vigil confirms this task is now complete:");
                System.out.println("  " + tasks[taskIndex]);
            } else if (line.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(line.substring(7).trim());
                int taskIndex = taskNumber - 1;
                tasks[taskIndex].markAsNotDone();
                System.out.println("Vigil notes this task is no longer complete:");
                System.out.println("  " + tasks[taskIndex]);
            } else if (line.startsWith("todo ")) {
                String description = line.substring(5).trim();

                if (description.isEmpty()) {
                    System.out.println("Task entry incomplete. Description required.");
                } else {
                    Task task = new Todo(description);
                    tasks[taskCount] = task;
                    taskCount++;
                    printTaskAdded(task, taskCount);
                }
            } else if (line.startsWith("deadline ")) {
                String rest = line.substring(9).trim();
                String[] parts = rest.split(" /by ", 2);

                if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                    System.out.println("Deadline entry is invalid. Use: deadline <description> /by <time>");
                } else {
                    Task task = new Deadline(parts[0].trim(), parts[1].trim());
                    tasks[taskCount] = task;
                    taskCount++;
                    printTaskAdded(task, taskCount);
                }
            } else if (line.startsWith("event ")) {
                String rest = line.substring(6).trim();
                String[] firstSplit = rest.split(" /from ", 2);

                if (firstSplit.length < 2 || firstSplit[0].trim().isEmpty()) {
                    System.out.println("Event entry is invalid. Use: event <desc> /from <start> /to <end>");
                } else {
                    String desc = firstSplit[0].trim();
                    String[] secondSplit = firstSplit[1].split(" /to ", 2);

                    if (secondSplit.length < 2 || secondSplit[0].trim().isEmpty() || secondSplit[1].trim().isEmpty()) {
                        System.out.println("Event entry is invalid. Use: event <desc> /from <start> /to <end>");
                    } else {
                        Task task = new Event(desc, secondSplit[0].trim(), secondSplit[1].trim());
                        tasks[taskCount] = task;
                        taskCount++;
                        printTaskAdded(task, taskCount);
                    }
                }

            } else {
                tasks[taskCount] = new Task(line);
                taskCount++;
                System.out.println("added: " + line);
            }

            printDivider();
        }

        printDivider();
        System.out.println("Goodbye. Hope to see you again soon! Vigil is going offline.");
        printDivider();

        scanner.close();
    }

    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println("Vigil acknowledges. Task successfully recorded:");
        System.out.println("  " + task);
        System.out.println(taskCount + " tasks currently under Vigil’s watch.");
    }

    private static void printDivider() {
        System.out.println("____________________________________________________________");
    }
}



