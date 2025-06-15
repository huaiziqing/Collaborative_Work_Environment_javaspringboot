package org.example.book.service;

import org.example.MyBatisUtil;
import org.example.book.repository.BookRepository;


/**
 * @author huaiziqng
 */

public class BookService {
    private final BookRepository bookRepository;

    public BookService() {
        // 通过 MyBatis 创建 Mapper 实例
        this.bookRepository = MyBatisUtil.getSqlSessionFactory().openSession(true).getMapper(BookRepository.class);
    }
}
