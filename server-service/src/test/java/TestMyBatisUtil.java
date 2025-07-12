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

            // 3. 测试 save
            int resourceId = testSave(repository);

            // 4. 测试 update
            testUpdate(repository, resourceId);

        }
    }

    // 测试 findById
    private static void testFindById(ResourceRepository repository) {
        ServerResource resource = repository.findById(3);
        if (resource != null){
            System.out.println("【findById】找到服务器： " + resource.getResourceId());
        } else {
            System.out.println("【findById】未找到服务器");
        }
    }

    // 测试 findAll
    private static void testFindAll(ResourceRepository repository) {
        List<ServerResource> resources = repository.findAll();
        System.out.println("【findAll】服务器总数：" + resources.size());
        for (ServerResource resource : resources) {
            System.out.println("【findAll】服务器" + resource.getTitle());
        }
    }

    // 测试 save
    private static int testSave(ResourceRepository repository) {
        ServerResource newResource = new ServerResource();
        newResource.setName("测试服务器");
        newResource.setDescription("测试服务器");
        newResource.setCpuCapacity(new BigDecimal("4"));
        newResource.setMemoryCapacity(new BigDecimal("8"));
        newResource.setStorageCapacity(new BigDecimal("100"));
        newResource.setLocation("测试位置");
        newResource.setStatus("idle");

        repository.save(newResource);
        System.out.println("【save】保存服务器成功，ID：" + newResource.getResourceId());
        return newResource.getResourceId();
    }

    // 测试 update
    private static void testUpdate(ResourceRepository repository, int resourceId) {
        ServerResource resource = repository.findById(resourceId);
        if (resource == null) {
            System.out.println("【update】未找到服务器");
            return;
        }

        // 修改服务器名
        String oldTitle = resource.getName();
        resource.setName(oldTitle + "--更新版");

        repository.update(resource);
        System.out.println("【update】服务器名 [" + oldTitle + "] 更新为 [" + resource.getName() + "]");

        // 验证更新
        ServerResource updatedResource = repository.findById(resourceId);
        if (updatedResource != null && updatedResource.getName().equals(resource.getName())) {
            System.out.println("【update】更新成功");
        } else {
            System.out.println("【update】更新失败");
        }
    }




}
