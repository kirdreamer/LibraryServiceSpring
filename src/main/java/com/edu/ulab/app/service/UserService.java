package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUserBookList(List<Long> userBookList, Long userId);

    List<Long> getUserBookListById(Long id);

    UserDto updateUser(UserDto userDto, Long id);

    UserDto getUserById(Long id);

    void deleteUserById(Long id);
}
