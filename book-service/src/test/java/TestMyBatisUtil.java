import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.MyBatisUtil;
import org.example.book.model.Book;
import org.example.book.repository.BookRepository;

import java.util.List;

public class TestMyBatisUtil {
    public static void main(String[] args) {
        SqlSessionFactory factory = MyBatisUtil.getSqlSessionFactory();

        try (SqlSession session = factory.openSession(true)) { // true 开启自动提交
            BookRepository repository = session.getMapper(BookRepository.class);

            // 1. 测试 findById
            testFindById(repository);

            // 2. 测试 findAll
            testFindAll(repository);

            // 3. 测试 save
            int newBookId = testSave(repository);

            // 4. 测试 update
            testUpdate(repository, newBookId);
        }
    }

    // 测试 findById
    private static void testFindById(BookRepository repository) {
        Book book = repository.findById(1);
        if (book != null) {
            System.out.println("【findById】找到图书: " + book.getTitle());
        } else {
            System.out.println("【findById】未找到ID=1的图书");
        }
    }

    // 测试 findAll
    private static void testFindAll(BookRepository repository) {
        List<Book> books = repository.findAll();
        System.out.println("【findAll】图书总数: " + books.size());
        for (Book book : books) {
            System.out.println(" - " + book.getTitle() + " (" + book.getAuthor() + ")");
        }
    }

    // 测试 save
    private static int testSave(BookRepository repository) {
        Book newBook = new Book();
        newBook.setIsbn("97800000000001");
        newBook.setTitle("测试用书");
        newBook.setAuthor("测试作者");
        newBook.setPublisher("测试出版社");
        newBook.setPublishYear((short) 2023);
        newBook.setCategoryId(1);
        newBook.setLocation("测试位置");
        newBook.setCallNumber("TEST-001");
        newBook.setCoverImagePath("/images/test.jpg");

        repository.save(newBook);
        System.out.println("【save】新增图书ID: " + newBook.getBookId());
        return newBook.getBookId();
    }

    // 测试 update
    private static void testUpdate(BookRepository repository, int bookId) {
        Book book = repository.findById(bookId);
        if (book == null) {
            System.out.println("【update】测试失败：未找到待更新的图书");
            return;
        }

        // 修改书名
        String oldTitle = book.getTitle();
        book.setTitle(oldTitle + "-更新版");

        repository.update(book);
        System.out.println("【update】图书标题从 [" + oldTitle + "] 更新为 [" + book.getTitle() + "]");

        // 验证更新
        Book updatedBook = repository.findById(bookId);
        if (updatedBook != null && updatedBook.getTitle().contains("-更新版")) {
            System.out.println("✅ 更新验证成功");
        } else {
            System.out.println("❌ 更新验证失败");
        }
    }
}

