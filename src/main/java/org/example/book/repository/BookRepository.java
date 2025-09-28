package org.example.book.repository;

import org.apache.ibatis.annotations.*;
import org.example.book.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookRepository {
    // 查询单本图书（支持自动映射）
    @Select("SELECT * FROM Book WHERE book_id = #{bookId}")
    Book findById(int bookId);

    // 查询所有图书（带关联借阅记录）
    @Select("SELECT * FROM Book")
    @Results({
            @Result(property = "bookId", column = "book_id"),
            @Result(property = "borrowedCount", column = "borrowed_count")
    })
    List<Book> findAll();

    // 新增图书（返回自动生成的主键）
    @Insert({
                    "INSERT INTO Book(isbn, title, author, publisher, " +
                    "publish_year, category_id, location, call_number, cover_image_path) " +
                    "VALUES (#{isbn},#{title},#{author},#{publisher}," +
                    "#{publishYear},#{categoryId},#{location},#{callNumber},#{coverImagePath})"
    })

    @Options(useGeneratedKeys = true, keyProperty = "bookId")
    void save(Book book);

    // 更新图书信息
    @Update({
            " UPDATE  Book SET isbn=#{isbn}," +
                    "title=#{title}," +
                    "author = #{author},"+
                    "publisher=#{publisher}," +
                    "publish_year=#{publishYear}," +
                    "category_id=#{categoryId}," +
                    "total_count=#{totalCount}," +
                    "borrowed_count=#{borrowedCount}," +
                    "location=#{location}," +
                    "call_number=#{callNumber}," +
                    "cover_image_path=#{coverImagePath} " +
                    "WHERE book_id=#{bookId}"
    })
    void update(Book book);

    // 删除图书
    @Delete("DELETE FROM Book WHERE book_id = #{bookId}")
    void delete(int bookId);
}