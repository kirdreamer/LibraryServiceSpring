package com.edu.ulab.app.repository.impl;

import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final Storage storage;

    @Override
    public BookEntity addBook(BookEntity bookEntity) {
        log.info("Received book {}", bookEntity);
        storage.saveBookInStorage(bookEntity);
        log.info("Book was added in Database by id {}", bookEntity.getId());
        return getBookById(bookEntity.getId());
    }

    @Override
    public BookEntity getBookById(Long id) {
        return storage.getBookStorage().get(id);
    }

    @Override
    public void deleteBookById(Long id) {
        storage.getBookStorage().remove(id);
        log.info("Book was deleted from Database by Id {}", id);
    }

    @Override
    public BookEntity updateBook(BookEntity bookEntity, Long id) {
        log.info("Received book {} with Id {}", bookEntity, id);
        bookEntity.setId(id);
        storage.getBookStorage().put(id, bookEntity);
        log.info("Book with Id {} was updated", id);
        return getBookById(id);
    }
}
