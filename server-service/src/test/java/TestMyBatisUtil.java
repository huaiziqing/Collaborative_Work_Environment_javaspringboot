import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.MyBatisUtil;
import org.example.server.model.ServerResource;
import org.example.server.repository.ResourceRepository;

import java.math.BigDecimal;
import java.util.List;

public class TestMyBatisUtil {
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();

        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            ResourceRepository repository = session.getMapper(ResourceRepository.class);

            // 1. 测试findById
            testFindById(repository);

            // 2. 测试findAll
            testFindAll(repository);

        }
    }

    private static void testFindById(ResourceRepository repository) {
        ServerResource resource = repository.findById(3);
        if (resource != null){
            System.out.println("[findById]找到服务器： " + resource.getResourceId());
        } else {
            System.out.println("[findById]未找到服务器");
        }
    }

    private static void testFindAll(ResourceRepository repository) {
        List<ServerResource> resources = repository.findAll();
        System.out.println("[findAll]服务器总数：" + resources.size());
        for (ServerResource resource : resources) {
            System.out.println("[findAll]服务器" + resource.getTitle());
        }
    }



}
