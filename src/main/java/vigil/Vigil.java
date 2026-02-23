package vigil;

import vigil.command.Command;
import vigil.exception.VigilException;
import vigil.parser.Parser;
import vigil.storage.Storage;
import vigil.task.TaskList;
import vigil.ui.Ui;

public class Vigil {

    private static final String DATA_FOLDER = "data";
    private static final String DATA_FILE = "vigil.txt";

    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

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