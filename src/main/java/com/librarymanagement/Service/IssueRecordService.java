package com.librarymanagement.Service;

import com.librarymanagement.Entity.Book;
import com.librarymanagement.Entity.IssueRecord;
import com.librarymanagement.Entity.User;
import com.librarymanagement.Respository.BookRepository;
import com.librarymanagement.Respository.IssueRecordRepository;
import com.librarymanagement.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IssueRecordService {
    @Autowired
    private IssueRecordRepository issueRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public IssueRecord issueTheBook(Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not Found"));
        if (book.getQuantity() <= 0 || !book.getIsAvailable()) {
            throw new RuntimeException("book Is Not available");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); // this will get user name
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));

        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setDueDate(LocalDate.now().plusDays(14));
        issueRecord.setIsReturned(false);
        issueRecord.setUser(user);
        issueRecord.setBook(book);

        book.setQuantity(book.getQuantity() - 1);
        if (book.getQuantity() == 0) {
            book.setIsAvailable(false);
        }

        bookRepository.save(book);
         return issueRecordRepository.save(issueRecord);
    }
    public  IssueRecord returnTheBook(long issueRecordId){
        IssueRecord issueRecord= issueRecordRepository.findById( issueRecordId)
        .orElseThrow(()->new RuntimeException("Issue record Not Found"));

        if(issueRecord.getIsReturned()){
            throw new RuntimeException("book is already returned");
        }
        Book book = issueRecord.getBook();
        book.setQuantity(book.getQuantity()+1);
        book.setIsAvailable(true);
        bookRepository.save(book);

//        issueRecord.setReturDate(LocalDate.now());
        issueRecord.setIsReturned(true);

        return issueRecordRepository.save(issueRecord);
    }
}

