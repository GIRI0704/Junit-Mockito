package com.mockoti.Junit.Controller;

import com.mockoti.Junit.Enitity.Book;
import com.mockoti.Junit.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookRepository bookRepository;

    @GetMapping("/get")
    public List<Book> getAllBookRecords() {
        return bookRepository.findAll();
    }

    @GetMapping(value = "/{bookId}")
    public Book getBookById(@PathVariable(value = "bookId") Long bookId) {
        return bookRepository.findById(bookId).get();
    }

    @PostMapping("/create")
    public Book createBookRecord(@RequestBody Book bookRecord) {
        return bookRepository.save(bookRecord);
    }

    @PutMapping("/update")
    public Book updateBookRecord(@RequestBody Book bookRecord) {
        if (bookRecord == null || bookRecord.getBookId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "BookRecord or ID must not be null!");
        }

        Optional<Book> optionalBook = bookRepository.findById(bookRecord.getBookId());
        if (!optionalBook.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ID: " + bookRecord.getBookId() + " does not exist.");
        }

        Book existingBookRecord = optionalBook.get();
        existingBookRecord.setName(bookRecord.getName());
        existingBookRecord.setSummary(bookRecord.getSummary());
        existingBookRecord.setRating(bookRecord.getRating());

        return bookRepository.save(existingBookRecord);
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id) throws Exception{
        if(bookRepository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found to delete");
        }
        bookRepository
                .deleteById(id);
        return "success";
    }


}
