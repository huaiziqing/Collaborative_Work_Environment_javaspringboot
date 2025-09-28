package org.example.server.controller;

import org.example.server.model.ServerResource;
import org.example.server.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    @Autowired
    private ResourceRepository resourceRepository;

    @GetMapping
    public List<ServerResource> getAllResources() {
        return resourceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ServerResource getResourceById(@PathVariable int id) {
        return resourceRepository.findById(id);
    }

    @PostMapping
    public void addResource(@RequestBody ServerResource resource) {
        resourceRepository.save(resource);
    }

    @PutMapping("/{id}")
    public void updateResource(@PathVariable int id, @RequestBody ServerResource resource) {
        resource.setResourceId(id);
        resourceRepository.update(resource);
    }

    @DeleteMapping("/{id}")
    public void deleteResource(@PathVariable int id) {
        resourceRepository.delete(id);
    }
}