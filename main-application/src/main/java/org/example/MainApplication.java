package org.example;

import org.example.book.model.Book;
import org.example.book.service.BookService;
import org.example.book.repository.BookRepository;
import org.example.server.model.ServerResource;
import org.example.server.service.ResourceService;
import org.example.server.repository.ResourceRepository;

import java.util.List;

/**
 * @author huaiziqng
 */

// 模拟的数据库访问实现
class InMemoryBookRepository implements BookRepository {
    @Override
    public Book findById(int bookId) {
        // 实现简单的内存数据获取
        return null;
    }

    @Override
    public List<Book> findAll() {
        // 实现简单的内存数据获取
        return null;
    }

    @Override
    public void save(Book book) {
        // 实现简单的内存存储
    }

    @Override
    public void update(Book book) {
        // 实现简单的内存更新
    }

    @Override
    public void delete(int bookId) {
        // 实现简单的内存删除
    }
}

class InMemoryResourceRepository implements ResourceRepository {
    @Override
    public ServerResource findById(int resourceId) {
        // 实现简单的内存数据获取
        return null;
    }

    @Override
    public List<ServerResource> findAll() {
        // 实现简单的内存数据获取
        return null;
    }

    @Override
    public void save(ServerResource resource) {
        // 实现简单的内存存储
    }

    @Override
    public void update(ServerResource resource) {
        // 实现简单的内存更新
    }

    @Override
    public void delete(int resourceId) {
        // 实现简单的内存删除
    }
}

public class MainApplication {
    public static void main(String[] args) {
        // 初始化图书服务
        BookRepository bookRepository = new InMemoryBookRepository();
        BookService bookService = new BookService(bookRepository);

        // 初始化服务器资源服务
        ResourceRepository resourceRepository = new InMemoryResourceRepository();
        ResourceService resourceService = new ResourceService(resourceRepository);

        // 示例：添加一本新书
        Book newBook = new Book();
        newBook.setTitle("Java编程");
        newBook.setAuthor("James Gosling");
        newBook.setPublisher("机械工业出版社");
        newBook.setPublishYear((short) 2020);
        newBook.setCategoryId(2); // 技术类
        newBook.setTotalCount(5);
        newBook.setLocation("A区4排");
        newBook.setCallNumber("TP312/789");

        bookService.addBook(newBook);
        System.out.println("Added new book: " + newBook.getTitle());

        // 示例：添加一台新服务器
        ServerResource newServer = new ServerResource();
        newServer.setName("高性能计算服务器");
        newServer.setDescription("用于科学计算的高性能服务器");
        newServer.setCpuCapacity(new java.math.BigDecimal("128.0"));
        newServer.setMemoryCapacity(new java.math.BigDecimal("1024.0"));
        newServer.setStorageCapacity(new java.math.BigDecimal("20000.0"));
        newServer.setLocation("数据中心A-机架1");
        newServer.setStatus("idle");

        resourceService.addResource(newServer);
        System.out.println("Added new server: " + newServer.getName());
    }
}
