package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        Person user = userMapper.userDtoToUserEntity(userDto);
        log.info("Mapped user: {}", user);
        Person savedUser = userRepository.save(user);
        log.info("Saved user: {}", savedUser);
        return userMapper.userEntityToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        Person user = userMapper.userDtoToUserEntity(userDto);
        user.setId(userId);
        log.info("Mapped user: {}", user);
        Person savedUser = userRepository.save(user);
        log.info("User was updated: {}", user);
        return userMapper.userEntityToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::userEntityToUserDto)
                .orElseThrow(() -> new NotFoundException("User wasn't found"));
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Trying to delete user by id {} with data: {}", id, getUserById(id));
        userRepository.deleteById(id);
    }
}
