package org.example.server.model;

import java.math.BigDecimal;

/**
 * @author huaiziqng
 */

public class ServerResource {
    private int resourceId;
    private String name;
    private String description;
    private BigDecimal cpuCapacity;
    private BigDecimal memoryCapacity;
    private BigDecimal storageCapacity;
    private String location;
    private String status; // idle, in_use, maintenance

    // Getters and Setters
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCpuCapacity() {
        return cpuCapacity;
    }

    public void setCpuCapacity(BigDecimal cpuCapacity) {
        this.cpuCapacity = cpuCapacity;
    }

    public BigDecimal getMemoryCapacity() {
        return memoryCapacity;
    }

    public void setMemoryCapacity(BigDecimal memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    public BigDecimal getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(BigDecimal storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return "ServerResource{" +
                "resourceId=" + resourceId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cpuCapacity=" + cpuCapacity +
                ", memoryCapacity=" + memoryCapacity +
                ", storageCapacity=" + storageCapacity +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
