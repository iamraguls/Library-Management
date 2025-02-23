package com.example.librarymanagement.controller;

import com.example.librarymanagement.entity.IssueRecord;
import com.example.librarymanagement.service.IssueRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IssueRecordController {

    @Autowired
    private IssueRecordService issueRecordService;

    @GetMapping("/issuebook/{bookId}")
    public IssueRecord issueTheBook(@PathVariable Long bookId){
        return issueRecordService.issueTheBook(bookId);
    }

    @PostMapping("/returnbook/{issueRecordId}")
    public IssueRecord returnTheBook(@PathVariable Long issueRecordId){
        return issueRecordService.returnTheBook(issueRecordId);
    }
}
