package com.librarymanagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table (name= "issuerecords")
public class IssueRecord {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Boolean isReturned;

    @ManyToOne                          // this class data has many books for one user
    @JoinColumn (name= "user_id")
    private  User user;


    @ManyToOne
    @JoinColumn (name = " book_id")
    private Book book;
}
