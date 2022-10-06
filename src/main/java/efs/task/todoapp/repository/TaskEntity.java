package efs.task.todoapp.repository;

import java.util.Objects;
import java.util.UUID;

public class TaskEntity {
    private UserEntity userEntity;
    private TaskJSON taskJSON;
    private UUID uuid;

    public TaskEntity(TaskJSON taskJSON, UserEntity userEntity) {
        this.userEntity = userEntity;
        this.taskJSON = taskJSON;
        this.uuid = UUID.randomUUID();
    }


    public UUID getUuid() {
        return uuid;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public TaskJSON getTaskJSON() {
        return taskJSON;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(userEntity, that.userEntity) && Objects.equals(taskJSON, that.taskJSON) && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEntity, taskJSON, uuid);
    }
}
