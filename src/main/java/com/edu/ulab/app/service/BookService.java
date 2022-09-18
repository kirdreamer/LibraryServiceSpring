package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;

public interface BookService {
    BookDto createBook(BookDto userDto);

    void updateBook(BookDto userDto, Long id);

    BookDto getBookById(Long id);

    void deleteBookById(Long id);
}
