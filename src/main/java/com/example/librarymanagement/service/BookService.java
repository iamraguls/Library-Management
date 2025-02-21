package com.example.librarymanagement.service;

import com.example.librarymanagement.dto.BookDto;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(()->new RuntimeException("Book not found"));
    }

    public Book addBook(BookDto bookDto) {
        Book book = new Book();
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setAvailable(bookDto.getAvailable());
        book.setIsbn(bookDto.getIsbn());
        book.setAvailable(bookDto.getAvailable());
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookDto bookDto) {
        Book oldBook = bookRepository.findById(id).orElseThrow(()->new RuntimeException("Book not found"));
        oldBook.setAuthor(bookDto.getAuthor());
        oldBook.setTitle(bookDto.getTitle());
        oldBook.setIsbn(bookDto.getIsbn());
        oldBook.setAvailable(bookDto.getAvailable());
        oldBook.setAvailable(bookDto.getAvailable());
        return bookRepository.save(oldBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
