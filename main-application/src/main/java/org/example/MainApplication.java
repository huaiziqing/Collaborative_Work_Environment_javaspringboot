package org.example;

import org.example.book.model.Book;
import org.example.book.service.BookService;
import org.example.book.repository.BookRepository;
import org.example.server.model.ServerResource;
import org.example.server.service.ResourceService;
import org.example.server.repository.ResourceRepository;

import java.sql.*;
import java.util.*;

/**
 * @author huaiziqng
 */

public class MainApplication {

    public static void main(String[] args) {
        String libraryDbUrl = "jdbc:mysql://120.26.114.28:3306/library_rental_system";
        String serverDbUrl = "jdbc:mysql://120.26.114.28:3306/server_rental_system";
        String user = "connect";
        String password = "tute@alydmysql";
        try {
            // 图书系统数据库测试
            try (Connection conn = DriverManager.getConnection(libraryDbUrl, user, password);
                 Statement stmt = conn.createStatement()) {

                // 查询图书表数据
                ResultSet rs = stmt.executeQuery("SELECT * FROM Book");
                System.out.println("===== Book表数据 =====");
                while (rs.next()) {
                    // 按示例格式输出
                    System.out.printf("%s,%s,%s,%s\n",
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("publisher")
                    );
                }
            } catch (SQLException e) {
                System.err.println("数据库查询错误: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
