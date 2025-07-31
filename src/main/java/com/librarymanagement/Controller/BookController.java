package com.librarymanagement.Controller;

import com.librarymanagement.DTO.BookDTO;
import com.librarymanagement.Entity.Book;
import com.librarymanagement.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @GetMapping("/getallbooks")
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }
    @GetMapping("/ getBookById/{id}")
    public  ResponseEntity <Book> getBookById (@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping("/addbook")
    @PreAuthorize("hasRole('ADMIN')")       // if the user is log in as admin then only this api will work
    public ResponseEntity<Book> addBook(@RequestBody BookDTO bookDTO){
        return  ResponseEntity.ok(bookService.addBook(bookDTO));
    }
    @PutMapping("/updatebook/{id}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<Book> updateBook(@PathVariable Long id,@RequestBody BookDTO bookDTO ){
        return ResponseEntity.ok(bookService.updatebook(id,bookDTO));
    }
    @DeleteMapping("/deletebook/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity <Void> deleteBookById(@PathVariable Long id){
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }

}
