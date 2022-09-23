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
    public UserDto addUser(UserDto userDto) {
        log.info("Received User {}", userDto);
        userDto.setId(storage.saveUserInStorage(userMapper.userDtoToUserEntity(userDto)));
        log.info("User was added in Database by id {}", userDto.getId());
        return getUser(userDto.getId());
    }

    @Override
    public UserDto updateUserBookList(List<Long> userBookList, Long userId) {
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
    public UserDto getUser(Long userId) {
        return userMapper.userEntityToUserDto(storage.getUserStorage().get(userId));
    }

    @Override
    public void deleteUser(Long userId) {
        storage.getUserStorage().remove(userId);
        log.info("User by Id {} was deleted from Database", userId);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        log.info("Received User {} with Id {}", userDto, userId);
        userDto.setId(userId);
        storage.getUserStorage().put(
                userDto.getId(),
                userMapper.userDtoToUserEntity(userDto)
        );
        log.info("User with Id {} was updated", userId);
        return getUser(userId);
    }
}
