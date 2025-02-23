package com.example.librarymanagement.service;

import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.entity.IssueRecord;
import com.example.librarymanagement.entity.User;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.IssueRecordRepository;
import com.example.librarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IssueRecordService {

    @Autowired
    private IssueRecordRepository issueRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public IssueRecord issueTheBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(()->new RuntimeException("Book not found"));
        if(book.getQuantity()<=0 || !book.getAvailable()){
            throw new RuntimeException("Book is not available");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("Username not found"));
        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setDueDate(LocalDate.now().plusDays(14));
        issueRecord.setReturned(false);
        issueRecord.setUser(user);
        issueRecord.setBook(book);
        book.setQuantity(book.getQuantity()-1);
        if(book.getQuantity()<=0){
            book.setAvailable(false);
        }
        bookRepository.save(book);
        return issueRecordRepository.save(issueRecord);
    }

    public IssueRecord returnTheBook(Long issueRecordId) {
        IssueRecord issueRecord = issueRecordRepository.findById(issueRecordId).orElseThrow(()->new RuntimeException("issue Record Not found"));
        if(issueRecord.getReturned()){
            throw new RuntimeException("Book is already returned");
        }
        Book book = issueRecord.getBook();
        book.setQuantity(book.getQuantity()+1);
        book.setAvailable(true);
        bookRepository.save(book);
        issueRecord.setReturnDate(LocalDate.now());
        issueRecord.setReturned(true);
        return issueRecordRepository.save(issueRecord);
    }
}
