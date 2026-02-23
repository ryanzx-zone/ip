package vigil.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import vigil.task.Task;
import vigil.task.TaskList;

public class Ui {

    private static final String DIVIDER =
            "_______________________________________________________________________________";

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public void showWelcome() {
        String logo = """
                ██╗   ██╗██╗ ██████╗ ██╗██╗
                ██║   ██║██║██╔════╝ ██║██║
                ██║   ██║██║██║  ███╗██║██║
                ╚██╗ ██╔╝██║██║   ██║██║██║
                 ╚████╔╝ ██║╚██████╔╝██║███████╗
                  ╚═══╝  ╚═╝ ╚═════╝ ╚═╝╚══════╝
                """;

        System.out.println(logo);
        showDivider();
        System.out.println("Hello! Vigil is online.");
        System.out.println("What can I do for you today?");
        showDivider();
    }

    public void showGoodbye() {
        showDivider();
        System.out.println("Goodbye. Hope to see you again soon! Vigil is going offline.");
        showDivider();
    }

    public void showDivider() {
        System.out.println(DIVIDER);
    }

    public void showError(String message) {
        System.out.println("Vigil alert: " + message);
    }

    public void showLoadingError(String message) {
        System.out.println("Vigil alert: " + message);
        System.out.println("Vigil will start with an empty task list.");
    }

    public void showTaskList(TaskList tasks) {
        System.out.println("Vigil scan complete. Here's your task list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Vigil acknowledges. Task successfully recorded:");
        System.out.println("  " + task);
        System.out.println(taskCount + " tasks currently under Vigil's watch.");
    }

    public void showMarkDone(Task task) {
        System.out.println("Vigil confirms this task is now complete:");
        System.out.println("  " + task);
    }

    public void showUnmarkDone(Task task) {
        System.out.println("Vigil notes this task is no longer complete:");
        System.out.println("  " + task);
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Vigil confirms termination. Task removed:");
        System.out.println("  " + task);
        System.out.println(taskCount + " tasks currently under Vigil's watch.");
    }

    public void showTasksOnDate(ArrayList<Task> tasks, LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        System.out.println("Vigil scan for " + formattedDate + ":");
        if (tasks.isEmpty()) {
            System.out.println("No tasks found on this date.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }
}