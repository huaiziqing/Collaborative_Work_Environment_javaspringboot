package org.example.book.repository;

import org.example.book.model.Book;

import java.util.List;

/**
 * @author huaiziqng
 */
//接口
public interface BookRepository {
    Book findById(int bookId);
    List<Book> findAll();
    void save(Book book);
    void update(Book book);
    void delete(int bookId);
}
