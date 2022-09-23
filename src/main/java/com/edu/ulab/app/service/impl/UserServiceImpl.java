package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
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

    @Override
    public UserDto createUser(UserDto userDto) {
        return userRepository.addUser(userDto);
    }

    @Override
    public UserDto updateUserBookList(List<Long> userBookList, Long userId) {
        return userRepository.updateUserBookList(userBookList, userId);
    }

    @Override
    public List<Long> getUserBookListById(Long id) {
        return userRepository.getUserBookList(id);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        return userRepository.updateUser(userDto, id);
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.getUser(id);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteUser(id);
    }
}
