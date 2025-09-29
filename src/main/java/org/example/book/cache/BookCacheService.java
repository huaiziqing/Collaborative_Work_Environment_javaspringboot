package org.example.book.cache;

import com.github.pagehelper.PageInfo;
import org.example.book.model.Book;

import java.util.List;

public interface BookCacheService {

    Book findByIdWithCache(int bookId);
    PageInfo<Book> findAllWithCache(int pageNum, int pageSize);

// 未实现：数据更新后清除缓存， 批量操作后清除所有缓存， 定时任务清除缓存
    void evictBookCache(int bookId);
    void evictAllBookCache();

}
