package org.example.book.service;

import org.example.book.model.Book;
import org.example.book.repository.BookRepository;

import java.util.List;

/**
 * @author huaiziqng
 */

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBookById(int bookId) {
        return bookRepository.findById(bookId);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(Book book) {
        bookRepository.update(book);
    }

    public void deleteBook(int bookId) {
        bookRepository.delete(bookId);
    }
}
