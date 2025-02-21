package com.example.librarymanagement.service;

import com.example.librarymanagement.entity.IssueRecord;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.IssueRecordRepository;
import com.example.librarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueRecordService {

    @Autowired
    private IssueRecordRepository issueRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public IssueRecord issueTheBook(Long bookId) {
        return
    }

    public IssueRecord returnTheBook(Long issueRecordId) {
        return
    }
}
