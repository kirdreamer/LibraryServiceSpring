package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.UserEntity;

import java.util.List;

public interface UserRepository {

    UserEntity addUser(UserEntity userEntity);

    UserEntity updateUserBookList(List<Long> userBookList, Long userId);

    List<Long> getUserBookList(Long id);

    UserEntity getUser(Long userId);

    void deleteUser(Long userId);

    UserEntity updateUser(UserEntity userEntity, Long userId);
}
