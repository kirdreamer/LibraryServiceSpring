package com.edu.ulab.app.repository.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.UserMapper;
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
    private final UserMapper userMapper;

    @Override
    public void addUser(UserDto userDto) {
        userDto.setId(Storage.userIdCounter++);
        log.info("Received User {}", userDto);
        storage.getUserStorage().put(
                userDto.getId(),
                userMapper.userDtoToUserEntity(userDto)
        );
        log.info("User was added in Database");
    }

    @Override
    public void updateBookIdList(List<Long> bookIdList, Long userId) {
        log.info("User {} received new list of book ids: {}", userId, bookIdList);
        storage.getUserStorage().get(userId).setBooksIdList(bookIdList);
        log.info("Update was successful");
    }

    @Override
    public List<Long> getUserBooksIdListById(Long id) {
        return storage.getUserStorage().get(id).getBooksIdList();
    }

    @Override
    public UserDto getUser(Long userId) {
        return userMapper.userEntityToUserDto(storage.getUserStorage().get(userId));
    }

    @Override
    public void deleteUser(Long userId) {
        storage.getUserStorage().remove(userId);
        log.info("User by Id {} was deleted from Database", userId);
    }

    @Override
    public void updateUser(UserDto userDto, Long userId) {
        log.info("Received User {} with Id {}", userDto, userId);
        userDto.setId(userId);
        storage.getUserStorage().put(
                userDto.getId(),
                userMapper.userDtoToUserEntity(userDto)
        );
        log.info("User with Id {} was updated", userId);
    }
}
