package com.librarymanagement.Controller;

import com.librarymanagement.Entity.IssueRecord;
import com.librarymanagement.Service.IssueRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/issuerecord")
public class IssueRecordController {

    @Autowired
    private IssueRecordService issueRecordService;

    @PostMapping("/issuethebook/{bookid}")
    public ResponseEntity<IssueRecord> issueTheBook(@PathVariable Long bookId){

        return ResponseEntity.ok(issueRecordService.issueTheBook(bookId));

    }
     @PostMapping("/returnthebook/{issueRecordId}")
    public ResponseEntity<IssueRecord> returnTheBook(@PathVariable Long issueRecordId){

        return ResponseEntity.ok(issueRecordService.returnTheBook(issueRecordId));
    }


}

