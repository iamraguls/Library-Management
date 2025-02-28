package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.BookDto;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    @PostMapping("/book")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // ✅ Use this
    public Book addBook(@RequestBody BookDto bookDto){
        return bookService.addBook(bookDto);
    }

    @PutMapping("/book/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // ✅ Use this
    public Book updateBook(@PathVariable Long id, @RequestBody BookDto bookDto){
        return bookService.updateBook(id,bookDto);
    }

    @DeleteMapping("/book/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // ✅ Use this
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }

}
