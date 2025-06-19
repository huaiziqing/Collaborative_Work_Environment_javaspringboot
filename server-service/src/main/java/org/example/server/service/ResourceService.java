package org.example.server.service;

import org.example.server.model.ServerResource;
import org.example.server.repository.ResourceRepository;

import java.util.List;

/**
 * @author huaiziqng
 */

public class ResourceService {
    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public ServerResource getResourceById(int resourceId) {
        return resourceRepository.findById(resourceId);
    }

    public List<ServerResource> getAllResources() {
        return resourceRepository.findAll();
    }

    public void addResource(ServerResource resource) {
        resourceRepository.save(resource);
    }

    public void updateResource(ServerResource resource) {
        resourceRepository.update(resource);
    }

    public void deleteResource(int resourceId) {
        resourceRepository.delete(resourceId);
    }
}
