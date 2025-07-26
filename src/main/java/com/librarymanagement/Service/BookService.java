package com.librarymanagement.Service;

import com.librarymanagement.Respository.BookRepository;
import com.librarymanagement.DTO.BookDTO;
import com.librarymanagement.Entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository ;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookById( long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Book Not Found"));
        return book;
    }
    public Book addBook(BookDTO bookDTO){
        Book book= new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setIsbn(bookDTO.getIsbn());
        book.setIsAvailable(bookDTO.getIsAvailable());
        book.setQuantity(bookDTO.getQuantity());
         return  bookRepository.save(book);
    }

    public  Book updatebook( long id, BookDTO bookDTO){
        Book oldBook= bookRepository.findById(id).orElseThrow(()-> new RuntimeException("book not Found"));

        oldBook.setTitle(bookDTO.getTitle());
        oldBook.setAuthor(bookDTO.getAuthor());
        oldBook.setIsbn(bookDTO.getIsbn());
        oldBook.setIsAvailable(bookDTO.getIsAvailable());
        oldBook.setQuantity(bookDTO.getQuantity());

         return bookRepository.save(oldBook);
    }

    public void deleteBookById( long id){
        bookRepository.deleteById(id);
    }
}
