package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.BookEntity;

public interface BookRepository {

    BookEntity addBook(BookEntity bookEntity);

    BookEntity getBookById(Long id);

    void deleteBookById(Long id);

    BookEntity updateBook(BookEntity bookEntity, Long id);
}
