package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImplTemplate implements UserService {
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;
    private final BookServiceImplTemplate bookServiceImplTemplate;

    @Override
    public UserDto createUser(UserDto userDto) {
        final String INSERT_SQL = "INSERT INTO PERSON(FULL_NAME, TITLE, AGE) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setLong(3, userDto.getAge());
                    return ps;
                }, keyHolder);

        userDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        userDto.getBookList().stream()
                .peek(book -> book.setUserId(userDto.getId()))
                .toList()
                .forEach(bookServiceImplTemplate::createBook);

        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        final String UPDATE_SQL = "UPDATE PERSON SET FULL_NAME=?, TITLE=?, AGE=? WHERE ID=?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(UPDATE_SQL, new String[]{"id"});
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setLong(3, userDto.getAge());
                    ps.setLong(4, id);
                    return ps;
                },
                keyHolder);

        bookServiceImplTemplate.getAllBooksByUserId(id)
                .forEach(book -> bookServiceImplTemplate.deleteBookById(book.getId()));

        userDto.getBookList().stream()
                .peek(book -> book.setUserId(id))
                .toList()
                .forEach(bookServiceImplTemplate::createBook);

        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {
        final String SELECT_SQL = "SELECT ID, FULL_NAME, TITLE, AGE FROM PERSON WHERE ID = ?";
        try {
            Person resultPerson = jdbcTemplate.queryForObject(
                    SELECT_SQL,
                    (rs, rowNum) -> {
                        Person person = new Person();
                        person.setId(rs.getLong("ID"));
                        person.setFullName(rs.getString("FULL_NAME"));
                        person.setTitle(rs.getString("TITLE"));
                        person.setAge(rs.getInt("AGE"));
                        return person;
                    },
                    id
            );
            return userMapper.userEntityToUserDto(resultPerson);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException("User wasn't found");
        }
    }

    @Override
    public void deleteUserById(Long id) {
        final String DELETE_SQL = "DELETE FROM PERSON WHERE ID=?";
        bookServiceImplTemplate.getAllBooksByUserId(id)
                        .forEach(book -> bookServiceImplTemplate.deleteBookById(book.getId()));
        jdbcTemplate.update(DELETE_SQL, id);
    }
}
