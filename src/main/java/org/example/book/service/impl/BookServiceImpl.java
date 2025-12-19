package org.example.book.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.book.service.BookService;
import org.example.book.model.Book;
import org.example.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    BookRepository bookRepository = null;

    // 查询所有图书 ( 分页查询 )
    @Override
    public PageInfo<Book> findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Book> books = bookRepository.findAll();
        return new PageInfo<>(books);
    }

    @Override
    public Book findById(int bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void update(Book book) {
        bookRepository.update(book);
    }

    @Override
    public void delete(int bookId) {
        bookRepository.delete(bookId);
    }
}
