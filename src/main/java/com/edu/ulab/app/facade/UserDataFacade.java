package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.BadRequestBodyException;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.BookResponse;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        if (userBookRequest.getUserRequest() == null)
            throw new BadRequestBodyException("User Body is empty");

        log.info("Received request to create user with books: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        if (userBookRequest.getBookRequests() == null) {
            log.info("User hasn't any books");
            return UserBookResponse.builder()
                    .userResponse(userMapper.userDtoToUserResponse(
                            userService.getUserById(createdUser.getId())
                    )).build();
        }

        List<Long> userBookList = getUserBookListFromFilteredAndAddedInDatabaseRequest(
                userBookRequest,
                createdUser.getId()
        );
        log.info("Collected book ids: {}", userBookList);

        log.info(
                "User's book list was updated. Actual data:{}",
                userService.updateUserBookList(userBookList, createdUser.getId())
        );

        return UserBookResponse.builder()
                .userResponse(userMapper.userDtoToUserResponse(
                        userService.getUserById(createdUser.getId())
                ))
                .bookResponse(getAllBooksByUserId(createdUser.getId())).build();
    }

    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest, Long userId) {
        log.info("Received request to update user with books by id {}", userId);
        if (userBookRequest.getUserRequest() == null)
            throw new BadRequestBodyException("User Body is empty");
        if (userService.getUserById(userId) == null)
            throw new NotFoundException("User wasn't found");

        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        List<Long> userBookList = userService.getUserBookListById(userId);
        userService.updateUser(userDto, userId);
        if (userBookList != null)
            userBookList.forEach(bookService::deleteBookById);

        if (userBookRequest.getBookRequests() == null) {
            log.info("User doesn't have any books");
            return UserBookResponse.builder()
                    .userResponse(userMapper.userDtoToUserResponse(
                            userService.getUserById(userId)
                    )).build();
        }

        userBookList = getUserBookListFromFilteredAndAddedInDatabaseRequest(
                userBookRequest,
                userId
        );
        log.info("Collected book ids: {}", userBookList);

        log.info(
                "User's book list was updated. Actual data:{}",
                userService.updateUserBookList(userBookList, userId)
        );

        return UserBookResponse.builder()
                .userResponse(userMapper.userDtoToUserResponse(
                        userService.getUserById(userId)
                ))
                .bookResponse(getAllBooksByUserId(userId)).build();
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        log.info("Received request to get user with books by id {}", userId);
        if (userService.getUserById(userId) == null)
            throw new NotFoundException("User wasn't found");

        if (userService.getUserBookListById(userId) == null)
            return UserBookResponse.builder().userResponse(userMapper.userDtoToUserResponse(
                    userService.getUserById(userId)
            )).build();

        return UserBookResponse.builder()
                .userResponse(userMapper.userDtoToUserResponse(
                        userService.getUserById(userId)
                ))
                .bookResponse(getAllBooksByUserId(userId)).build();
    }

    public void deleteUserWithBooks(Long userId) {
        log.info("Received request to delete user with books by id {}", userId);

        if (userService.getUserById(userId) == null)
            throw new NotFoundException("User wasn't found");

        List<Long> bookIdList = userService.getUserBookListById(userId);

        userService.deleteUserById(userId);

        if (bookIdList != null)
            bookIdList.forEach(id -> {
                log.info("Trying to delete book by Id {}", id);
                bookService.deleteBookById(id);
            });
    }

    private List<Long> getUserBookListFromFilteredAndAddedInDatabaseRequest(UserBookRequest userBookRequest, Long userId) {
        return userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(userId))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
    }

    private List<BookResponse> getAllBooksByUserId(Long userId) {
        return userService.getUserBookListById(userId)
                .stream()
                .map(bookService::getBookById)
                .map(bookMapper::bookDtoToBookResponse)
                .toList();
    }
}
