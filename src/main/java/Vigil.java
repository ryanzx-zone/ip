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

        while (true) {
            String line = scanner.nextLine();

            if (line.equalsIgnoreCase("bye")) {
                break;
            }

            System.out.println("____________________________________________________________");
            System.out.println(" " + line);
            System.out.println("____________________________________________________________");
        }

        System.out.println("____________________________________________________________");
        System.out.println("Goodbye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");

        scanner.close();
    }
}

