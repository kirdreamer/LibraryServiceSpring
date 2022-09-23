package com.edu.ulab.app.repository;

import com.edu.ulab.app.dto.BookDto;

public interface BookRepository {

    BookDto addBook(BookDto bookDto);

    BookDto getBookById(Long id);

    void deleteBookById(Long id);

    BookDto updateBook(BookDto bookDto, Long id);
}
