package com.nestify.pg.entity;

public class Payment {

    private Long id;
    private String tenantName;
    private String roomNumber;
    private double amount;
    private String paymentDate;

    public Payment(Long id, String tenantName, String roomNumber, double amount, String paymentDate) {
        this.id = id;
        this.tenantName = tenantName;
        this.roomNumber = roomNumber;
        this.amount = amount;
        this.paymentDate = paymentDate;
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

    public double getAmount() {
        return amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setId(Long id) {
        this.id = id;
    }
}