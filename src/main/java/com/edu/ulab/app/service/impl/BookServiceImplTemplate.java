package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImplTemplate implements BookService {

    private final JdbcTemplate jdbcTemplate;
    private final BookMapper bookMapper;

    @Override
    public BookDto createBook(BookDto bookDto) {
        final String INSERT_SQL = "INSERT INTO BOOK(TITLE, AUTHOR, PAGE_COUNT, USER_ID) VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, bookDto.getTitle());
                    ps.setString(2, bookDto.getAuthor());
                    ps.setLong(3, bookDto.getPageCount());
                    ps.setLong(4, bookDto.getUserId());
                    return ps;
                },
                keyHolder);

        bookDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto, Long id) {
        final String UPDATE_SQL = "UPDATE BOOK SET TITLE=?, AUTHOR=?, PAGE_COUNT=?, USER_ID=? WHERE ID=?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(UPDATE_SQL, new String[]{"id"});
                    ps.setString(1, bookDto.getTitle());
                    ps.setString(2, bookDto.getAuthor());
                    ps.setLong(3, bookDto.getPageCount());
                    ps.setLong(4, bookDto.getUserId());
                    return ps;
                },
                keyHolder);
        return bookDto;
    }

    @Override
    public List<BookDto> getAllBooksByUserId(Long userId) {
        final String SELECT_SQL = "SELECT ID, TITLE, AUTHOR, PAGE_COUNT, USER_ID FROM BOOK WHERE USER_ID = ?";
        return jdbcTemplate.query(
                        SELECT_SQL,
                        (rs, rowNum) -> {
                            Book book = new Book();
                            book.setId(rs.getLong("ID"));
                            book.setTitle(rs.getString("TITLE"));
                            book.setAuthor(rs.getString("AUTHOR"));
                            book.setPageCount(rs.getLong("PAGE_COUNT"));
                            book.setUserId(rs.getLong("USER_ID"));
                            return book;
                        },
                        userId
                ).stream()
                .map(bookMapper::bookEntityToBookDto)
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        final String SELECT_SQL = "SELECT ID, TITLE, AUTHOR, PAGE_COUNT, USER_ID FROM BOOK WHERE ID = ?";
        return bookMapper.bookEntityToBookDto(jdbcTemplate.queryForObject(
                SELECT_SQL,
                (rs, rowNum) -> {
                    Book book = new Book();
                    book.setId(rs.getLong("ID"));
                    book.setTitle(rs.getString("TITLE"));
                    book.setAuthor(rs.getString("AUTHOR"));
                    book.setPageCount(rs.getLong("PAGE_COUNT"));
                    book.setUserId(rs.getLong("USER_ID"));
                    return book;
                },
                id
        ));
    }

    @Override
    public void deleteBookById(Long id) {
        final String DELETE_SQL = "DELETE FROM BOOK WHERE ID=?";
        jdbcTemplate.update(DELETE_SQL, id);
    }
}
