package efs.task.todoapp.repository;

import java.util.Objects;

public class TaskJSON {
    private String due;
    private String description;


    public TaskJSON(String due, String desc) {
        this.due = due;
        this.description = desc;
    }

    public String getDue() {
        return due;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskJSON taskJSON = (TaskJSON) o;
        return Objects.equals(due, taskJSON.due) && Objects.equals(description, taskJSON.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(due, description);
    }
}
