package com.nestify.pg.entity;

public class Payment {

    private Long id;
    private String tenantName;
    private String roomNumber;
    private double amount;
    private String paymentDate;

    private String dueDate;
    private String status; 

    public Payment(Long id, String tenantName, String roomNumber,
                   double amount, String paymentDate,
                   String dueDate, String status) {

        this.id = id;
        this.tenantName = tenantName;
        this.roomNumber = roomNumber;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
        this.status = status;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Payment(Long id, String tenantName, String roomNumber,
                   double amount, String paymentDate) {

        this.id = id;
        this.tenantName = tenantName;
        this.roomNumber = roomNumber;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.dueDate = "N/A";
        this.status = "PENDING";
    }

    public Long getId() { return id; }
    public String getTenantName() { return tenantName; }
    public String getRoomNumber() { return roomNumber; }
    public double getAmount() { return amount; }
    public String getPaymentDate() { return paymentDate; }
    public String getDueDate() { return dueDate; }
    public String getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setStatus(String status) { this.status = status; }
}