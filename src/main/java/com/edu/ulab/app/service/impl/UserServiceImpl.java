package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        return userMapper.userEntityToUserDto(
                userRepository.addUser(userMapper.userDtoToUserEntity(userDto))
        );
    }

    @Override
    public UserDto updateUserBookList(List<Long> userBookList, Long userId) {
        return userMapper.userEntityToUserDto(
                userRepository.updateUserBookList(userBookList, userId)
        );
    }

    @Override
    public List<Long> getUserBookListById(Long id) {
        return userRepository.getUserBookList(id);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        return userMapper.userEntityToUserDto(
                userRepository.updateUser(userMapper.userDtoToUserEntity(userDto), id)
        );
    }

    @Override
    public UserDto getUserById(Long id) {
        return userMapper.userEntityToUserDto(userRepository.getUser(id));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteUser(id);
    }
}
