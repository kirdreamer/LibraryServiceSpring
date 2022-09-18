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
        userRepository.addUser(userDto);
        return userDto;
    }

    @Override
    public void updateBookIdList(List<Long> bookIdList, Long userId) {
        userRepository.updateBookIdList(bookIdList, userId);
    }

    @Override
    public List<Long> getBookIdListById(Long id) {
        return userRepository.getUserBooksIdListById(id);
    }

    @Override
    public void updateUser(UserDto userDto, Long id) {
        userRepository.updateUser(userDto, id);
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
