# Vigil User Guide

Vigil is a **command-line task management chatbot** that helps you track todos, deadlines, and events. It saves your tasks automatically so they persist between sessions.

## Quick Start

1. Ensure you have **Java 17** or above installed.
2. Download the latest `Vigil.jar` from [here](https://github.com/ryanzx-zone/ip/releases).
3. Open a terminal, navigate to the folder containing the jar file, and run:
   ```
   java -jar vigil.jar
   ```
4. Type a command and press Enter. Refer to the [Features](#features) section below for details on each command.

## Features

> **Notes about the command format:**
> - Words in `UPPER_CASE` are parameters to be supplied by the user.
> - Parameters must follow the order shown.

### Adding a todo: `todo`

Adds a task with no date.

Format: `todo DESCRIPTION`

Example: `todo read book`

```
Vigil acknowledges. Task successfully recorded:
  [T][ ] read book
1 tasks currently under Vigil's watch.
```

### Adding a deadline: `deadline`

Adds a task with a due date.

Format: `deadline DESCRIPTION /by DUE_DATE`

- If `DUE_DATE` is in `yyyy-MM-dd` format (e.g., `2025-03-15`), it will be displayed as `Mar 15 2025`.
- Otherwise, the text is stored and displayed as-is (e.g., `sunday`).

Examples:
- `deadline submit report /by sunday`
- `deadline return book /by 2025-03-15`

```
Vigil acknowledges. Task successfully recorded:
  [D][ ] return book (by: Mar 15 2025)
2 tasks currently under Vigil's watch.
```

### Adding an event: `event`

Adds a task with a start and end time.

Format: `event DESCRIPTION /from START /to END`

- Dates in `yyyy-MM-dd` format are displayed in `MMM d yyyy` format.
- Other text is stored and displayed as-is.

Example: `event conference /from 2025-03-14 /to 2025-03-16`

```
Vigil acknowledges. Task successfully recorded:
  [E][ ] conference (from: Mar 14 2025 to: Mar 16 2025)
3 tasks currently under Vigil's watch.
```

### Listing all tasks: `list`

Displays all tasks as a numbered list.

Format: `list`

```
Vigil scan complete. Here's your task list:
1. [T][ ] read book
2. [D][ ] return book (by: Mar 15 2025)
3. [E][ ] conference (from: Mar 14 2025 to: Mar 16 2025)
```

### Marking a task as done: `mark`

Marks the specified task as complete.

Format: `mark TASK_NUMBER`

Example: `mark 1`

```
Vigil confirms this task is now complete:
  [T][X] read book
```

### Unmarking a task: `unmark`

Marks the specified task as not done.

Format: `unmark TASK_NUMBER`

Example: `unmark 1`

```
Vigil notes this task is no longer complete:
  [T][ ] read book
```

### Deleting a task: `delete`

Removes the specified task from the list.

Format: `delete TASK_NUMBER`

Example: `delete 2`

```
Vigil confirms termination. Task removed:
  [D][ ] return book (by: Mar 15 2025)
2 tasks currently under Vigil's watch.
```

### Finding tasks by keyword: `find`

Searches for tasks whose descriptions contain the given keyword. The search is case-insensitive.

Format: `find KEYWORD`

Example: `find book`

```
Vigil scan for "book":
1. [T][ ] read book
2. [E][ ] book club (from: 2pm to: 4pm)
```

### Viewing tasks on a date: `schedule`

Lists all deadlines due on and events occurring on the specified date.

Format: `schedule DATE`

- `DATE` must be in `yyyy-MM-dd` format.

Example: `schedule 2025-03-15`

```
Vigil scan for Mar 15 2025:
1. [D][ ] return book (by: Mar 15 2025)
2. [E][ ] conference (from: Mar 14 2025 to: Mar 16 2025)
```

### Exiting the program: `bye`

Exits Vigil.

Format: `bye`

## Saving Data

Tasks are saved automatically to `data/vigil.txt` after every change. There is no need to save manually.

## Editing the Data File

Task data is saved as a text file at `data/vigil.txt`. Advanced users may edit this file directly.

The format is pipe-delimited, one task per line:
```
T | 0 | read book
D | 0 | return book | 2025-03-15
E | 1 | conference | 2025-03-14 | 2025-03-16
```

> ⚠️ **Caution:** If the data file format is invalid, Vigil will discard the corrupted entries. Back up the file before editing.

## Command Summary

| Action       | Format                                          |
|--------------|------------------------------------------------|
| **Todo**     | `todo DESCRIPTION`                              |
| **Deadline** | `deadline DESCRIPTION /by DUE_DATE`             |
| **Event**    | `event DESCRIPTION /from START /to END`         |
| **List**     | `list`                                          |
| **Mark**     | `mark TASK_NUMBER`                              |
| **Unmark**   | `unmark TASK_NUMBER`                            |
| **Delete**   | `delete TASK_NUMBER`                             |
| **Find**     | `find KEYWORD`                                  |
| **Schedule** | `schedule DATE`                                 |
| **Exit**     | `bye`                                           |
