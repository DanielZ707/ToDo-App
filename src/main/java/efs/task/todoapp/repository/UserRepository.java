package efs.task.todoapp.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class UserRepository implements Repository<String, UserEntity> {

    Map<String, UserEntity> users = new HashMap<>();

    @Override
    public String save(UserEntity userEntity) throws DuplicatedData {
        UserEntity user = users.putIfAbsent(userEntity.getUsername(), userEntity);
        if (user != null) {
            throw new DuplicatedData();
        }
        return userEntity.getUsername();
    }

    @Override
    public UserEntity query(String username) {
        return users.get(username);
    }

    @Override
    public List<UserEntity> query(Predicate<UserEntity> condition) {
        return null;
    }

    @Override
    public UserEntity update(String s, UserEntity userEntity) {
        return null;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }
}
