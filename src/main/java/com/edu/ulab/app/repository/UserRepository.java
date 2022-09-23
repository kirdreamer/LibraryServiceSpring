package com.edu.ulab.app.repository;

import com.edu.ulab.app.dto.UserDto;

import java.util.List;

public interface UserRepository {

    UserDto addUser(UserDto userDto);

    UserDto updateUserBookList(List<Long> userBookList, Long userId);

    List<Long> getUserBookList(Long id);

    UserDto getUser(Long userId);

    void deleteUser(Long userId);

    UserDto updateUser(UserDto userDto, Long userId);
}
