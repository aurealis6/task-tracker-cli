import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskStore {

    public static void saveToFile(String jsonText) {
        try {
            Files.writeString(Path.of("src/tasks.json"), jsonText);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static String listToJson(List<Task> taskList) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < taskList.size(); i++) {
            sb.append(taskToJson(taskList.get(i)));
            if (i != taskList.size() - 1) {
                sb.append(",\n");
            }
        }
        sb.append("\n]");
        return sb.toString();
    }

    public static String taskToJson(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t{\n");
        sb.append("\t\t\"id\": " + task.getId() + ", \n");
        sb.append("\t\t\"description\": \"" + task.getDescription() + "\", \n");
        sb.append("\t\t\"status\": \"" + task.getStatusText() + "\", \n");
        sb.append("\t\t\"createdAt\": \"" + task.getCreatedAt()  + "\", \n");
        sb.append("\t\t\"updatedAt\": \"" + task.getUpdatedAt() + "\"\n");
        sb.append("\t}");
        return sb.toString();
    }

    public static String JsonToString() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("src/tasks.json"))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    public static List<Task> loadTasks() throws IOException {
        List<Task> taskList = new ArrayList<>();

        if (!Files.exists(Path.of("src/tasks.json"))) {
            return taskList;
        }

        String fullJsonString = JsonToString();
        fullJsonString = fullJsonString.replace("[", "").replace("]", "").trim();

        if (fullJsonString.isEmpty()) {
            return taskList;
        }

        String[] splitIntoJSONObjects = fullJsonString.split("}");

        splitIntoJSONObjects[0] = splitIntoJSONObjects[0].replace("{", "").trim();

        for (int i = 1; i < splitIntoJSONObjects.length; i++) {
            splitIntoJSONObjects[i] = splitIntoJSONObjects[i].replace(",\t{", "").trim();
        }

        for (String s : splitIntoJSONObjects) {
            String[] taskTokens = s.split(",");
            for (int i = 0; i < taskTokens.length; i++) {
                taskTokens[i] = taskTokens[i].trim();
            }
            int id = Integer.parseInt(taskTokens[0].substring(6));
            String description = taskTokens[1].substring(16, taskTokens[1].length() - 1);
            String status = taskTokens[2].substring(11, taskTokens[2].length() - 1);
            String createdAt = taskTokens[3].substring(14, taskTokens[3].length() - 1);
            String updatedAt = taskTokens[4].substring(14, taskTokens[4].length() - 1);

            Task.Status statusTask = null;
            switch (status) {
                case "todo":
                    statusTask = Task.Status.TODO;
                    break;
                case "in-progress":
                    statusTask = Task.Status.IN_PROGRESS;
                    break;
                case "done":
                    statusTask = Task.Status.DONE;
                    break;
            }

            Task t = new Task(id, description);
            t.setStatus(statusTask);
            t.setCreatedAt(LocalDateTime.parse(createdAt));
            t.setUpdatedAt(LocalDateTime.parse(updatedAt));

            taskList.add(t);
        }
        return taskList;
    }
}