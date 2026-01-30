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
        System.out.println("____________________________________________________________");
        System.out.println("Hello! Vigil is online.");
        System.out.println("What can I do for you today?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);

        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (true) {
            String line = scanner.nextLine();

            if (line.equalsIgnoreCase("bye")) {
                break;
            }

            System.out.println("____________________________________________________________");

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
            } else {
                tasks[taskCount] = new Task(line);
                taskCount++;
                System.out.println("added: " + line);
            }

            System.out.println("____________________________________________________________");
        }

        System.out.println("____________________________________________________________");
        System.out.println("Goodbye. Hope to see you again soon! Vigil is going offline.");
        System.out.println("____________________________________________________________");

        scanner.close();
    }
}


