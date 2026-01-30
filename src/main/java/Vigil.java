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
        System.out.println("Hello! I'm Vigil");
        System.out.println("What can I do for you today?");
        System.out.println("____________________________________________________________");

        Scanner scanner = new Scanner(System.in);

        String[] tasks = new String[100];
        int taskCount = 0;

        while (true) {
            String line = scanner.nextLine();

            if (line.equalsIgnoreCase("bye")) {
                break;
            }

            System.out.println("____________________________________________________________");

            if (line.equalsIgnoreCase("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[taskCount] = line;
                taskCount++;

                System.out.println("added: " + line);
            }

            System.out.println("____________________________________________________________");
        }

        System.out.println("____________________________________________________________");
        System.out.println("Goodbye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");

        scanner.close();
    }
}


