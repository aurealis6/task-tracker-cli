import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    enum Status {
        TODO,
        IN_PROGRESS,
        DONE
    }

    private int id;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = Status.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatusText() {
        switch (status) {
            case TODO:
                return "todo";
            case IN_PROGRESS:
                return "in-progress";
            case DONE:
                return "done";
        }
        throw new IllegalStateException("Unexpected status: " + status);
    }

    public String getCreatedAtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return createdAt.format(formatter);
    }

    public String getUpdatedAtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return updatedAt.format(formatter);
    }

    @Override
    public String toString() {
        return "Task " + getId() + ": " + getDescription() + " (" + getStatusText() + ")"  + " | created: " + getCreatedAtText() + ", updated: " + getUpdatedAtText() + ".";
    }
}


