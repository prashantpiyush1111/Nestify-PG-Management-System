package com.nestify.pg.entity;

public class Complaint {

    private Long id;
    private String tenantName;
    private String roomNumber;
    private String issue;
    private String status;

    public Complaint(Long id, String tenantName, String roomNumber, String issue, String status) {
        this.id = id;
        this.tenantName = tenantName;
        this.roomNumber = roomNumber;
        this.issue = issue;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getIssue() {
        return issue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}