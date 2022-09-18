package com.edu.ulab.app.repository;

import com.edu.ulab.app.dto.BookDto;

public interface BookRepository {

    void addBook(BookDto bookDto);

    BookDto getBookById(Long id);

    void deleteBookById(Long id);

    void updateBook(BookDto bookDto, Long id);

}
