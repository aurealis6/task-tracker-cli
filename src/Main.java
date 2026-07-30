import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<Task> taskList;

    public static void main(String[] args) throws IOException {
        taskList = TaskStore.loadTasks();

        if (args.length == 0) {
            System.out.println("No command provided.");
            return;
        }

        String[] parameters = args;
        String action = parameters[0];

        switch (action) {
            case "add":
                addTask(parameters);
                break;
            case "update":
                updateTask(parameters);
                break;
            case "delete":
                deleteTask(parameters);
                break;
            case "mark-in-progress":
                markInProgress(parameters);
                break;
            case "mark-done":
                markDone(parameters);
                break;
            case "list":
                list(parameters);
                break;
        }

        TaskStore.saveToFile(TaskStore.listToJson(taskList));

    }

    // add "Book Hotel"
    private static void addTask(String[] parameters) {
        if (parameters.length != 2) {
            System.out.println("Usage: add <description>");
            return;
        }
        String description = parameters[1];
        int id = generateId();
        Task task = new Task(id, description);
        taskList.add(task);
        System.out.println("Task added successfully (ID: " + id + ")");
    }

    // get the last Task-element in taskList and get its ID. New id is +1
    private static int generateId() {
        int maxId = 0;
        for (Task t : taskList) {
            if (t.getId() > maxId) {
                maxId = t.getId();
            }
        }
        return maxId + 1;
    }

    // update 3 "Cancel Hotel"
    private static void updateTask(String[] parameters) {
        if (parameters.length != 3) {
            System.out.println("Usage: update <id> <description>");
            return;
        }
        Integer id = parseId(parameters[1]);
        if (id == null) {
            return;
        }
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId() == id) {
                taskList.get(i).setDescription(parameters[2]);
                taskList.get(i).setUpdatedAt(LocalDateTime.now());
                System.out.println("Task " + id + " is updated to \"" + parameters[2] + "\"");
                return;
            }
        }
        System.out.println("Error: Could not find ID " + id + ".");
    }

    private static Integer parseId(String id) {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + id + "\" is not a valid task ID.");
            return null;
        }
    }

    // delete 3
    private static void deleteTask(String[] parameters) {
        if (parameters.length != 2) {
            System.out.println("Usage: delete <id>");
            return;
        }

        Integer id = parseId(parameters[1]);
        if (id == null) {
            return;
        }

        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId() == id) {
                taskList.remove(i);
                System.out.println("Task removed successfully (ID: " + id + ").");
                return;
            }
        }
        System.out.println("Error: Could not find ID " + id + ".");

    }

    // list (done/todo/in-progress)
    private static void list(String[] parameters) {
        if (parameters.length > 2) {
            System.out.println("Usage: list (<status>)");
            return;
        }
        if (parameters.length == 1) {
            for (Task t : taskList) {
                System.out.println(t);
            }
        } else if (parameters.length == 2) {
            String status = parameters[1];
            if (status.equals("done")) {
                for (Task t : taskList) {
                    if (t.getStatus().equals(Task.Status.DONE))
                    System.out.println(t);
                }
            } else if (status.equals("todo")) {
                for (Task t : taskList) {
                    if (t.getStatus().equals(Task.Status.TODO))
                        System.out.println(t);
                }
            } else if (status.equals("in-progress")) {
                for (Task t : taskList) {
                    if (t.getStatus().equals(Task.Status.IN_PROGRESS))
                        System.out.println(t);
                }
            } else {
                System.out.println("Error: status-change not valid.");
            }
        }
    }

    // mark-done 2
    private static void markDone(String[] parameters) {
        if (parameters.length != 2) {
            System.out.println("Usage: mark-done <id>");
            return;
        }

        Integer id = parseId(parameters[1]);
        if (id == null) {
            return;
        }

        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId() == id) {
                taskList.get(i).setStatus(Task.Status.DONE);
                taskList.get(i).setUpdatedAt(LocalDateTime.now());
                System.out.println("Task " + id + " is marked as done!");
                return;
            }
        }
        System.out.println("Error: Could not find ID " + id + ".");
    }

    // mark-in-progress 2
    private static void markInProgress(String[] parameters) {
        if (parameters.length != 2) {
            System.out.println("Usage: mark-in-progress <id>");
            return;
        }

        Integer id = parseId(parameters[1]);
        if (id == null) {
            return;
        }

        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId() == id) {
                taskList.get(i).setStatus(Task.Status.IN_PROGRESS);
                taskList.get(i).setUpdatedAt(LocalDateTime.now());
                System.out.println("Task " + id + " is marked as in-progress!");
                return;
            }
        }
        System.out.println("Could not find a task with ID " + id + ".");
    }
}