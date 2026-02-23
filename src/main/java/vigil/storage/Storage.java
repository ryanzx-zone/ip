package vigil.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import vigil.exception.VigilException;
import vigil.task.Deadline;
import vigil.task.Event;
import vigil.task.Task;
import vigil.task.Todo;

/**
 * Handles loading and saving of tasks to a text file.
 * Tasks are stored in a pipe-delimited format, one task per line.
 * For example: {@code T | 0 | read book} or {@code D | 1 | report | sunday}.
 */
public class Storage {

    private static final String TYPE_TODO = "T";
    private static final String TYPE_DEADLINE = "D";
    private static final String TYPE_EVENT = "E";

    private static final String SEPARATOR = " | ";
    private static final String SPLIT_REGEX = "\\s*\\|\\s*";

    private final Path filePath;

    public Storage(String folderName, String fileName) {
        this.filePath = Path.of(folderName, fileName);
    }

    /**
     * Loads tasks from the save file.
     * Returns an empty array if the file does not exist.
     *
     * @return An array of tasks loaded from the file.
     * @throws VigilException If the file exists but cannot be read or contains corrupted data.
     */
    public Task[] load() throws VigilException {
        if (!Files.exists(filePath)) {
            return new Task[0];
        }

        List<Task> tasks = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }
                tasks.add(parseLine(trimmed));
            }
        } catch (IOException e) {
            throw new VigilException("Failed to load tasks: " + e.getMessage());
        }

        return tasks.toArray(new Task[0]);
    }

    /**
     * Saves the given tasks to the save file, creating the parent directory if needed.
     *
     * @param tasks Array of tasks to save.
     * @param taskCount Number of tasks in the array to save.
     * @throws VigilException If the file cannot be written to.
     */
    public void save(Task[] tasks, int taskCount) throws VigilException {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            List<String> lines = new ArrayList<>();
            for (int i = 0; i < taskCount; i++) {
                lines.add(formatTask(tasks[i]));
            }

            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new VigilException("Failed to save tasks: " + e.getMessage());
        }
    }

    /**
     * Parses a single line from the save file into a Task object.
     * Expected formats:
     * <ul>
     *   <li>{@code T | 0 | description}</li>
     *   <li>{@code D | 0 | description | by}</li>
     *   <li>{@code E | 0 | description | from | to}</li>
     * </ul>
     *
     * @param line The line to parse.
     * @return The corresponding Task object.
     * @throws VigilException If the line format is invalid or unrecognised.
     */
    private static Task parseLine(String line) throws VigilException {
        String[] parts = line.split(SPLIT_REGEX);

        if (parts.length < 3) {
            throw new VigilException("Corrupted save data: " + line);
        }

        String type = parts[0].trim();
        boolean isDone = parseDone(parts[1].trim(), line);
        String description = parts[2].trim();

        Task task;
        if (TYPE_TODO.equals(type)) {
            task = new Todo(description);
        } else if (TYPE_DEADLINE.equals(type)) {
            if (parts.length != 4) {
                throw new VigilException("Corrupted deadline data: " + line);
            }
            task = new Deadline(description, parts[3].trim());
        } else if (TYPE_EVENT.equals(type)) {
            if (parts.length != 5) {
                throw new VigilException("Corrupted event data: " + line);
            }
            task = new Event(description, parts[3].trim(), parts[4].trim());
        } else {
            throw new VigilException("Unknown task type in save data: " + line);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    private static boolean parseDone(String raw, String line) throws VigilException {
        if ("0".equals(raw)) {
            return false;
        }
        if ("1".equals(raw)) {
            return true;
        }
        throw new VigilException("Invalid done flag in save data: " + line);
    }

    private static String formatTask(Task task) throws VigilException {
        String doneFlag = task.isDone() ? "1" : "0";

        if (task instanceof Todo) {
            return TYPE_TODO + SEPARATOR + doneFlag + SEPARATOR + task.getDescription();
        }

        if (task instanceof Deadline deadline) {
            return TYPE_DEADLINE + SEPARATOR + doneFlag + SEPARATOR + deadline.getDescription()
                    + SEPARATOR + deadline.getBy();
        }

        if (task instanceof Event event) {
            return TYPE_EVENT + SEPARATOR + doneFlag + SEPARATOR + event.getDescription()
                    + SEPARATOR + event.getFrom()
                    + SEPARATOR + event.getTo();
        }

        throw new VigilException("Cannot save unknown task type: " + task.getClass().getSimpleName());
    }
}