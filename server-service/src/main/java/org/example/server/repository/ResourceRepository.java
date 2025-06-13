package org.example.server.repository;

import org.example.server.model.ServerResource;

import java.util.List;

/**
 * @author huaiziqng
 */

public interface ResourceRepository {
    ServerResource findById(int resourceId);
    List<ServerResource> findAll();
    void save(ServerResource resource);
    void update(ServerResource resource);
    void delete(int resourceId);
}
