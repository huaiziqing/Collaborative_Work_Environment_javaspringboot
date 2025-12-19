package org.example.book.cache.impl;

import com.github.pagehelper.PageInfo;
import org.example.book.service.BookService;
import org.example.book.cache.BookCacheService;
import org.example.book.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCacheServiceImpl implements BookCacheService {

    @Autowired
    private BookService bookService;

    @Override
    @Cacheable(value = "books", key = "#bookId", unless = "#result == null")
    public Book findByIdWithCache(int bookId) {
        return bookService.findById(bookId);
    }

    @Override
    @Cacheable(value = "books", key = "#pageNum + '_' + #pageSize", unless = "#result == null")
    public PageInfo<Book> findAllWithCache(int pageNum, int pageSize) {
        return bookService.findAll(pageNum, pageSize);
    }

    @Override
    public void evictBookCache(int bookId) {

    }

    @Override
    public void evictAllBookCache() {

    }


}
