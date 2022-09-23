package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto createBook(BookDto bookDto) {
        return bookMapper.bookEntityToBookDto(
                bookRepository.addBook(bookMapper.bookDtoToBookEntity(bookDto))
        );
    }

    @Override
    public BookDto updateBook(BookDto bookDto, Long id) {
        return bookMapper.bookEntityToBookDto(
                bookRepository.updateBook(bookMapper.bookDtoToBookEntity(bookDto), id)
        );
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookMapper.bookEntityToBookDto(bookRepository.getBookById(id));
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteBookById(id);
    }
}
