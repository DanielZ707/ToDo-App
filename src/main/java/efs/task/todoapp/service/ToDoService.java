package efs.task.todoapp.service;

import com.google.gson.JsonObject;
import efs.task.todoapp.repository.*;
import com.google.gson.Gson;
import efs.task.todoapp.service.handlers.IncorrectData;
import efs.task.todoapp.service.handlers.NoTask;
import efs.task.todoapp.service.handlers.NoUserOrWrongPassword;
import efs.task.todoapp.service.handlers.WrongUser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

public class ToDoService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public ToDoService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public String UserSave(String userJson) throws DuplicatedData, IncorrectData {

        UserEntity userEntity = new Gson().fromJson(userJson, UserEntity.class);
        if (userEntity == null) {
            throw new IncorrectData();
        }

        if (userEntity.getUsername() == null || userEntity.getPassword() == null || userEntity.getPassword().equals("") || userEntity.getUsername().equals("")) {
            throw new IncorrectData();
        }
        return userRepository.save(userEntity);
    }

    public String TaskSave(String userJson, UserEntity userEntity) throws IncorrectData, ParseException, NoUserOrWrongPassword {

        UserEntity user = userRepository.query(userEntity.getUsername());
        if (user == null || !(user.getUsername().equals(userEntity.getUsername())) || !(user.getPassword().equals(userEntity.getPassword()))) {
            throw new NoUserOrWrongPassword();
        }

        TaskJSON taskJSON = new Gson().fromJson(userJson, TaskJSON.class);
        if (taskJSON == null) {
            throw new IncorrectData();
        }
        if (taskJSON.getDescription() == null || taskJSON.getDescription().equals("")) {
            throw new IncorrectData();
        }
        if (taskJSON.getDue() != null) {
            DateFormat sdf = new SimpleDateFormat("uuuu-MM-dd");
            sdf.parse(taskJSON.getDue());
        }
        TaskEntity taskEntity = new TaskEntity(taskJSON, userEntity);
        UUID uuid = taskRepository.save(taskEntity);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", uuid.toString());
        String json = new Gson().toJson(jsonObject);
        return json;
    }

    public TaskEntity GetTask(UUID uuid, UserEntity userEntity) throws NoUserOrWrongPassword, NoTask, WrongUser {
        UserEntity user = userRepository.query(userEntity.getUsername());
        if (user == null || !(user.getUsername().equals(userEntity.getUsername())) || !(user.getPassword().equals(userEntity.getPassword()))) {
            throw new NoUserOrWrongPassword();
        }
        TaskEntity taskEntity = taskRepository.query(uuid);
        if (taskEntity == null) {
            throw new NoTask();
        }
        if (!(taskEntity.getUserEntity().getPassword().equals(userEntity.getPassword())) || !(taskEntity.getUserEntity().getUsername().equals(userEntity.getUsername()))) {
            throw new WrongUser();
        }
        return taskEntity;
    }

    public List<TaskEntity> GetTasks(UserEntity userEntity) throws NoUserOrWrongPassword {
        UserEntity user = userRepository.query(userEntity.getUsername());
        if (user == null || !(user.getUsername().equals(userEntity.getUsername())) || !(user.getPassword().equals(userEntity.getPassword()))) {
            throw new NoUserOrWrongPassword();
        }

        List<TaskEntity> taskEntities = taskRepository.query(taskEntity -> taskEntity.getUserEntity().getUsername().equals(userEntity.getUsername()));
        return taskEntities;
    }

    public TaskEntity UpdateTask(UUID uuid, String userJson, UserEntity userEntity) throws NoUserOrWrongPassword, NoTask, WrongUser {
        UserEntity user = userRepository.query(userEntity.getUsername());
        if (user == null || !(user.getUsername().equals(userEntity.getUsername())) || !(user.getPassword().equals(userEntity.getPassword()))) {
            throw new NoUserOrWrongPassword();
        }
        TaskEntity taskEntity2 = taskRepository.query(uuid);
        if (taskEntity2 == null) {
            throw new NoTask();
        }
        if (!(taskEntity2.getUserEntity().getPassword().equals(userEntity.getPassword())) || !(taskEntity2.getUserEntity().getUsername().equals(userEntity.getUsername()))) {
            throw new WrongUser();
        }
        TaskJSON taskJSON = new Gson().fromJson(userJson, TaskJSON.class);
        TaskEntity taskEntity = new TaskEntity(taskJSON, userEntity);
        TaskEntity taskEntity1 = taskRepository.update(uuid, taskEntity);
        System.out.println(taskEntity1.getTaskJSON().getDue());
        return taskEntity1;
    }

    public boolean DeleteTask(UUID uuid, UserEntity userEntity) throws NoTask, WrongUser, NoUserOrWrongPassword {
        UserEntity user = userRepository.query(userEntity.getUsername());
        if (user == null || !(user.getUsername().equals(userEntity.getUsername())) || !(user.getPassword().equals(userEntity.getPassword()))) {
            throw new NoUserOrWrongPassword();
        }
        TaskEntity taskEntity = taskRepository.query(uuid);
        if (taskEntity == null) {
            throw new NoTask();
        }
        if (!(taskEntity.getUserEntity().getPassword().equals(userEntity.getPassword())) || !(taskEntity.getUserEntity().getUsername().equals(userEntity.getUsername()))) {
            throw new WrongUser();
        }
        boolean deleted = taskRepository.delete(uuid);
        return deleted;
    }
}