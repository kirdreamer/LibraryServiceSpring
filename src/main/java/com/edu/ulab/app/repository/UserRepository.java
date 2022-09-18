package com.edu.ulab.app.repository;

import com.edu.ulab.app.dto.UserDto;

import java.util.List;

public interface UserRepository {

    void addUser(UserDto userDto);

    void updateBookIdList(List<Long> booksIdList, Long userId);

    List<Long> getUserBooksIdListById(Long id);

    UserDto getUser(Long userId);

    void deleteUser(Long userId);

    void updateUser(UserDto userDto, Long userId);
}
