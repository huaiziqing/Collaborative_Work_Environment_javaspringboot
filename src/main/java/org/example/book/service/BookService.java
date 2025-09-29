package org.example.book.service;

import com.github.pagehelper.PageInfo;
import org.example.book.model.Book;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface BookService {

    Book findById(int bookId);
    PageInfo<Book> findAll(int pageNum, int pageSize);
    void save(Book book);
    void update(Book book);
    void delete(int bookId);
}
