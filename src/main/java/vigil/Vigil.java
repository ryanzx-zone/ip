package vigil;

import vigil.command.Command;
import vigil.exception.VigilException;
import vigil.parser.Parser;
import vigil.storage.Storage;
import vigil.task.TaskList;
import vigil.ui.Ui;

/**
 * Represents the main entry point of the Vigil chatbot application.
 * Vigil is a task management chatbot that supports todos, deadlines,
 * and events with persistent storage.
 */
public class Vigil {

    private static final String DATA_FOLDER = "data";
    private static final String DATA_FILE = "vigil.txt";

    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Constructs a Vigil instance and loads saved tasks from the specified file.
     * If loading fails, starts with an empty task list instead.
     *
     * @param dataFolder Folder containing the save file.
     * @param dataFile Name of the save file.
     */
    public Vigil(String dataFolder, String dataFile) {
        ui = new Ui();
        storage = new Storage(dataFolder, dataFile);
        try {
            tasks = new TaskList(storage.load());
        } catch (VigilException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main command loop, reading and executing user commands
     * until an exit command is received.
     */
    public void run() {
        ui.showWelcome();

        while (ui.hasNextLine()) {
            String fullCommand = ui.readCommand();

            Command c;
            try {
                c = Parser.parse(fullCommand);
            } catch (VigilException e) {
                ui.showDivider();
                ui.showError(e.getMessage());
                ui.showDivider();
                continue;
            }

            if (c.isExit()) {
                break;
            }

            ui.showDivider();
            try {
                c.execute(tasks, ui, storage);
            } catch (VigilException e) {
                ui.showError(e.getMessage());
            }
            ui.showDivider();
        }

        ui.showGoodbye();
    }

    public static void main(String[] args) {
        new Vigil(DATA_FOLDER, DATA_FILE).run();
    }
}