# Task Tracker CLI

A simple command-line application for tracking tasks, built in Java. Tasks are stored in a local JSON file, with no external libraries used for JSON handling — all serialization and parsing is done manually.

Project idea: https://roadmap.sh/projects/task-tracker

Repository: https://github.com/aurealis6/task-tracker-cli

## Features

- Add, update, and delete tasks
- Mark a task as in progress or done
- List all tasks
- List tasks filtered by status (`todo`, `in-progress`, `done`)
- Tasks persist between runs in a `tasks.json` file
- Graceful error handling for invalid task IDs and malformed commands

## Requirements

- Java (JDK 17 or later recommended)
- No external libraries or dependencies

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/aurealis6/task-tracker-cli.git
   cd task-tracker-cli
   ```

2. Compile the source code:
   ```bash
   javac src/*.java -d out
   ```

## Usage

Run the app with `java -cp out Main <command> [arguments]`.

```bash
# Add a new task
java -cp out Main add "Buy groceries"
# Output: Task added successfully (ID: 1)

# Update a task's description
java -cp out Main update 1 "Buy groceries and cook dinner"

# Delete a task
java -cp out Main delete 1

# Mark a task as in progress or done
java -cp out Main mark-in-progress 1
java -cp out Main mark-done 1

# List all tasks
java -cp out Main list

# List tasks by status
java -cp out Main list todo
java -cp out Main list in-progress
java -cp out Main list done
```

## Task Properties

Each task stored in `tasks.json` has the following properties:

- `id` — a unique identifier for the task
- `description` — a short description of the task
- `status` — one of `todo`, `in-progress`, `done`
- `createdAt` — when the task was created
- `updatedAt` — when the task was last modified

## Project Structure

```
src/
├── Main.java        # Entry point; parses commands and dispatches actions
├── Task.java         # Task data model
└── TaskStore.java     # Handles reading/writing tasks.json (hand-written JSON serialization)
```

## Notes

- No external JSON libraries are used — `TaskStore.java` builds and parses JSON manually as plain text.
- If `tasks.json` doesn't exist yet, it will be created automatically on first use.
