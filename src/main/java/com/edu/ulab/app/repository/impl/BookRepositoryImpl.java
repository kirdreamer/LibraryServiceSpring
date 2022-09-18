package com.edu.ulab.app.repository.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.mapper.BookMapper;
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
    private final BookMapper bookMapper;

    @Override
    public void addBook(BookDto bookDto) {
        bookDto.setId(Storage.bookIdCounter++);
        log.info("Received book {}", bookDto);
        storage.getBookStorage().put(bookDto.getId(), bookMapper.bookDtoToBookEntity(bookDto));
        log.info("Book was added in Database");
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookMapper.bookEntityToBookDto(storage.getBookStorage().get(id));
    }

    @Override
    public void deleteBookById(Long id) {
        storage.getBookStorage().remove(id);
        log.info("Book was deleted from Database by Id {}", id);
    }

    @Override
    public void updateBook(BookDto bookDto, Long id) {
        log.info("Received book {} with Id {}", bookDto, id);
        bookDto.setId(id);
        storage.getBookStorage().put(id, bookMapper.bookDtoToBookEntity(bookDto));
        log.info("Book with Id {} was updated", id);
    }
}
