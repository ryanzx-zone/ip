package vigil.parser;

import vigil.command.Command;
import vigil.command.DeadlineCommand;
import vigil.command.DeleteCommand;
import vigil.command.EventCommand;
import vigil.command.ExitCommand;
import vigil.command.FindCommand;
import vigil.command.ListCommand;
import vigil.command.MarkCommand;
import vigil.command.ScheduleCommand;
import vigil.command.TodoCommand;
import vigil.command.UnmarkCommand;
import vigil.exception.VigilException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    private static final String COMMAND_TODO = "todo";
    private static final String COMMAND_MARK = "mark";
    private static final String COMMAND_UNMARK = "unmark";
    private static final String COMMAND_DEADLINE = "deadline";
    private static final String COMMAND_EVENT = "event";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_FIND = "find";
    private static final String COMMAND_SCHEDULE = "schedule";
    private static final String COMMAND_BYE = "bye";

    public static Command parse(String fullCommand) throws VigilException {
        if (fullCommand.equalsIgnoreCase(COMMAND_BYE)) {
            return new ExitCommand();
        }

        if (fullCommand.equalsIgnoreCase(COMMAND_LIST)) {
            return new ListCommand();
        }

        if (fullCommand.equals(COMMAND_TODO) || fullCommand.startsWith(COMMAND_TODO + " ")) {
            return parseTodo(fullCommand);
        }

        if (fullCommand.equals(COMMAND_DEADLINE) || fullCommand.startsWith(COMMAND_DEADLINE + " ")) {
            return parseDeadline(fullCommand);
        }

        if (fullCommand.equals(COMMAND_EVENT) || fullCommand.startsWith(COMMAND_EVENT + " ")) {
            return parseEvent(fullCommand);
        }

        if (fullCommand.equals(COMMAND_MARK) || fullCommand.startsWith(COMMAND_MARK + " ")) {
            return parseMark(fullCommand);
        }

        if (fullCommand.equals(COMMAND_UNMARK) || fullCommand.startsWith(COMMAND_UNMARK + " ")) {
            return parseUnmark(fullCommand);
        }

        if (fullCommand.equals(COMMAND_DELETE) || fullCommand.startsWith(COMMAND_DELETE + " ")) {
            return parseDelete(fullCommand);
        }

        if (fullCommand.equals(COMMAND_SCHEDULE) || fullCommand.startsWith(COMMAND_SCHEDULE + " ")) {
            return parseSchedule(fullCommand);
        }

        if (fullCommand.equals(COMMAND_FIND) || fullCommand.startsWith(COMMAND_FIND + " ")) {
            return parseFind(fullCommand);
        }

        throw new VigilException("Command not recognised.");
    }

    private static Command parseTodo(String fullCommand) throws VigilException {
        String description = fullCommand.equals(COMMAND_TODO)
                ? ""
                : fullCommand.substring(COMMAND_TODO.length()).trim();
        if (description.isEmpty()) {
            throw new VigilException("Task entry incomplete. A todo needs a description.");
        }
        return new TodoCommand(description);
    }

    private static Command parseDeadline(String fullCommand) throws VigilException {
        String rest = fullCommand.equals(COMMAND_DEADLINE)
                ? ""
                : fullCommand.substring(COMMAND_DEADLINE.length()).trim();
        String[] parts = rest.split(" /by ", 2);

        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new VigilException("Deadline entry is invalid. Use: deadline <description> /by <time>");
        }

        return new DeadlineCommand(parts[0].trim(), parts[1].trim());
    }

    private static Command parseEvent(String fullCommand) throws VigilException {
        String rest = fullCommand.equals(COMMAND_EVENT)
                ? ""
                : fullCommand.substring(COMMAND_EVENT.length()).trim();
        String[] firstSplit = rest.split(" /from ", 2);

        if (firstSplit.length < 2 || firstSplit[0].trim().isEmpty()) {
            throw new VigilException("Event entry is invalid. Use: event <desc> /from <start> /to <end>");
        }

        String desc = firstSplit[0].trim();
        String[] secondSplit = firstSplit[1].split(" /to ", 2);

        if (secondSplit.length < 2 || secondSplit[0].trim().isEmpty() || secondSplit[1].trim().isEmpty()) {
            throw new VigilException("Event entry is invalid. Use: event <desc> /from <start> /to <end>");
        }

        return new EventCommand(desc, secondSplit[0].trim(), secondSplit[1].trim());
    }

    private static Command parseMark(String fullCommand) throws VigilException {
        if (fullCommand.equals(COMMAND_MARK)) {
            throw new VigilException("Missing task number. Use: mark <task number>.");
        }
        return new MarkCommand(fullCommand.substring(COMMAND_MARK.length()).trim());
    }

    private static Command parseUnmark(String fullCommand) throws VigilException {
        if (fullCommand.equals(COMMAND_UNMARK)) {
            throw new VigilException("Missing task number. Use: unmark <task number>.");
        }
        return new UnmarkCommand(fullCommand.substring(COMMAND_UNMARK.length()).trim());
    }

    private static Command parseDelete(String fullCommand) throws VigilException {
        if (fullCommand.equals(COMMAND_DELETE)) {
            throw new VigilException("Missing task number. Use: delete <task number>.");
        }
        return new DeleteCommand(fullCommand.substring(COMMAND_DELETE.length()).trim());
    }

    private static Command parseSchedule(String fullCommand) throws VigilException {
        String rest = fullCommand.equals(COMMAND_SCHEDULE)
                ? ""
                : fullCommand.substring(COMMAND_SCHEDULE.length()).trim();
        if (rest.isEmpty()) {
            throw new VigilException("Missing date. Use: schedule <yyyy-MM-dd>");
        }
        try {
            LocalDate date = LocalDate.parse(rest);
            return new ScheduleCommand(date);
        } catch (DateTimeParseException e) {
            throw new VigilException("Invalid date format. Use: schedule <yyyy-MM-dd>");
        }
    }

    private static Command parseFind(String fullCommand) throws VigilException {
        String keyword = fullCommand.equals(COMMAND_FIND)
                ? ""
                : fullCommand.substring(COMMAND_FIND.length()).trim();
        if (keyword.isEmpty()) {
            throw new VigilException("Missing keyword. Use: find <keyword>");
        }
        return new FindCommand(keyword);
    }
}