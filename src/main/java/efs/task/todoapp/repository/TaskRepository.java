package efs.task.todoapp.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaskRepository implements Repository<UUID, TaskEntity> {

    Map<UUID, TaskEntity> tasks = new HashMap<>();

    @Override
    public UUID save(TaskEntity taskEntity) {
        UUID uuid = taskEntity.getUuid();
        tasks.put(uuid, taskEntity);
        return uuid;
    }

    @Override
    public TaskEntity query(UUID uuid) {
        return tasks.get(uuid);
    }

    @Override
    public List<TaskEntity> query(Predicate<TaskEntity> condition) {
        return tasks.values().stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    @Override
    public TaskEntity update(UUID uuid, TaskEntity taskEntity) {

        tasks.put(uuid, taskEntity);
        return taskEntity;
    }

    @Override
    public boolean delete(UUID uuid) {
        return tasks.remove(uuid) != null;
    }
}
