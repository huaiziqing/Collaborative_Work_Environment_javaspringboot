package org.example.server.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author huaiziqng
 */

public class RentRecord {
    private int recordId;
    private int applicationId;
    private int resourceId;
    private int userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime dueTime;
    private BigDecimal expectedCpu;
    private BigDecimal expectedMemory;
    private BigDecimal actualCpuUsage;
    private BigDecimal actualMemoryUsage;
    private String status; // borrowed, returned, overdue, paused
    private int pauseCount;

    // Getters and Setters
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(LocalDateTime dueTime) {
        this.dueTime = dueTime;
    }

    public BigDecimal getExpectedCpu() {
        return expectedCpu;
    }

    public void setExpectedCpu(BigDecimal expectedCpu) {
        this.expectedCpu = expectedCpu;
    }

    public BigDecimal getExpectedMemory() {
        return expectedMemory;
    }

    public void setExpectedMemory(BigDecimal expectedMemory) {
        this.expectedMemory = expectedMemory;
    }

    public BigDecimal getActualCpuUsage() {
        return actualCpuUsage;
    }

    public void setActualCpuUsage(BigDecimal actualCpuUsage) {
        this.actualCpuUsage = actualCpuUsage;
    }

    public BigDecimal getActualMemoryUsage() {
        return actualMemoryUsage;
    }

    public void setActualMemoryUsage(BigDecimal actualMemoryUsage) {
        this.actualMemoryUsage = actualMemoryUsage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPauseCount() {
        return pauseCount;
    }

    public void setPauseCount(int pauseCount) {
        this.pauseCount = pauseCount;
    }
}
