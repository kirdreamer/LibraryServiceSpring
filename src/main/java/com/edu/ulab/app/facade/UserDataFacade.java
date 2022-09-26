package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.BadRequestBodyException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    private UserDto mapUserBookRequestToUserDto(UserBookRequest userBookRequest) {
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        userDto.setBookList(getFilteredUserBookListFromUserBookRequest(userBookRequest));
        log.info("Mapped user request: {}", userDto);
        return userDto;
    }

    private List<BookDto> getFilteredUserBookListFromUserBookRequest(UserBookRequest userBookRequest) {
        return userBookRequest.getBookRequests() == null ?
                new ArrayList<>() :
                userBookRequest.getBookRequests()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(bookMapper::bookRequestToBookDto)
                        .toList();
    }

    private UserBookResponse getUserBookResponseWithBooksByUserId(Long userId) {
        return UserBookResponse.builder()
                .userResponse(userMapper.userDtoToUserResponse(
                        userService.getUserById(userId)
                ))
                .bookResponse(
                        bookService.getAllBooksByUserId(userId).stream()
                                .map(bookMapper::bookDtoToBookResponse)
                                .toList()
                ).build();
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        if (userBookRequest.getUserRequest() == null) {
            throw new BadRequestBodyException("User Body is empty");
        }
        log.info("Received request to create user with books: {}", userBookRequest);

        UserDto userDto = mapUserBookRequestToUserDto(userBookRequest);
        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        return getUserBookResponseWithBooksByUserId(createdUser.getId());
    }

    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest, Long userId) {
        if (userBookRequest.getUserRequest() == null)
            throw new BadRequestBodyException("User Body is empty");

        log.info("Received request to update user with books by id {}", userId);
        log.info("User by id {} with data {} will be updated", userId, userService.getUserById(userId));

        UserDto userDto = mapUserBookRequestToUserDto(userBookRequest);
        userService.updateUser(userDto, userId);

        return getUserBookResponseWithBooksByUserId(userId);
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        log.info("Received request to get user with books by id {}", userId);
        return getUserBookResponseWithBooksByUserId(userId);
    }

    public void deleteUserWithBooks(Long userId) {
        log.info("Received request to delete user with books by id {}", userId);
        userService.deleteUserById(userId);
    }
}