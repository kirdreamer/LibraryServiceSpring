package com.edu.ulab.app.repository.impl;

import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final Storage storage;

    @Override
    public UserEntity addUser(UserEntity userEntity) {
        log.info("Received User {}", userEntity);
        storage.saveUserInStorage(userEntity);
        log.info("User was added in Database by id {}", userEntity.getId());
        return getUser(userEntity.getId());
    }

    @Override
    public UserEntity updateUserBookList(List<Long> userBookList, Long userId) {
        log.info("User {} received new list of book ids: {}", userId, userBookList);
        storage.getUserStorage().get(userId).setBookList(userBookList);
        log.info("Update was successful");
        return getUser(userId);
    }

    @Override
    public List<Long> getUserBookList(Long id) {
        return storage.getUserStorage().get(id).getBookList();
    }

    @Override
    public UserEntity getUser(Long userId) {
        return storage.getUserStorage().get(userId);
    }

    @Override
    public void deleteUser(Long userId) {
        storage.getUserStorage().remove(userId);
        log.info("User by Id {} was deleted from Database", userId);
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity, Long userId) {
        log.info("Received User {} with Id {}", userEntity, userId);
        userEntity.setId(userId);
        storage.getUserStorage().put(
                userEntity.getId(),
                userEntity
        );
        log.info("User with Id {} was updated", userId);
        return getUser(userId);
    }
}
