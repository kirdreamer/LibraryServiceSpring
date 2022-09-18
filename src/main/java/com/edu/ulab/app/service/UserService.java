package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    void updateBookIdList(List<Long> bookIdList, Long userId);

    List<Long> getBookIdListById(Long id);

    void updateUser(UserDto userDto, Long id);

    UserDto getUserById(Long id);

    void deleteUserById(Long id);
}
