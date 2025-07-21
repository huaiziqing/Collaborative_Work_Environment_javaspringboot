package org.example.server.repository;


import org.apache.ibatis.annotations.*;
import org.example.server.model.ServerResource;

import java.util.List;

/**
 * @author huaiziqng , JiyliuI
 */

public interface ResourceRepository {
//    ServerResource findById(int resourceId);
//    List<ServerResource> findAll();
//    void save(ServerResource resource);
//    void update(ServerResource resource);
//    void delete(int resourceId);

    // 查询单个资源（支持自动映射）
    @Select("SELECT * FROM ServerResource WHERE resource_id = #{resourceId}")
    ServerResource findById(int resourceId);

    // 查询所有资源（支持自动映射）
    @Select("SELECT * FROM ServerResource")
    @Results({
            @Result(property = "resourceId", column = "resource_id"),
            @Result(property = "serverId", column = "server_id")
    })
    List<ServerResource> findAll();

    // 新增服务器
    @Insert({
            "INSERT INTO ServerResource(resource_id, name, description, cpu_capacity, " +
                    "memory_capacity, storage_capacity, location, status) " +
                    "VALUES (#{resourceId},#{name},#{description},#{cpuCapacity},"  +
                    "#{memoryCapacity},#{storageCapacity},#{location},#{status})"
    })
    @Options(useGeneratedKeys = true, keyColumn = "resource_id", keyProperty = "resourceId")
    void save(ServerResource resource);


    // 更新服务器信息
    @Update({
            " UPDATE  ServerResource SET name= #{name}," +
                    "status= #{status} " +
                    "WHERE resource_id= #{resourceId}"
    })
    void update(ServerResource resource);

    // 删除服务器
    @Delete("DELETE FROM ServerResource WHERE resource_id = #{resourceId}")
    void delete(int resourceId);


}
