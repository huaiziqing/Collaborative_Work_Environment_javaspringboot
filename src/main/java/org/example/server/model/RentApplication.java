package org.example.server.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author huaiziqng
 */

public class RentApplication {
    private int applicationId;
    private int resourceId;
    private int applicantId;
    private BigDecimal expectedCpu;
    private BigDecimal expectedMemory;
    private String purpose;
    private LocalDateTime applyTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int duration;
    private String status; // pending, approved, rejected, canceled
    private Integer auditorId;
    private LocalDateTime auditTime;
    private String rejectReason;

    // Getters and Setters
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

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDateTime getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(LocalDateTime applyTime) {
        this.applyTime = applyTime;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public LocalDateTime getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(LocalDateTime auditTime) {
        this.auditTime = auditTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
