package org.example.book.controller;

import com.github.pagehelper.PageInfo;
import org.example.book.service.BookService;
import org.example.book.cache.BookCacheService;
import org.example.book.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookCacheService bookCacheService;

    //设置分页参数(默认1 - 4)
    @GetMapping
    public PageInfo<Book> getAllBooks(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "4") int size) {
        return bookCacheService.findAllWithCache(page, size);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookCacheService.findByIdWithCache(id);
    }

    @PostMapping
    public void addBook(@RequestBody Book book) {
        bookService.save(book);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable int id, @RequestBody Book book) {
        book.setBookId(id);
        bookService.update(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable int id) {
        bookService.delete(id);
    }
}