package com.nestify.pg.service;

import com.nestify.pg.entity.Payment;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {

    private List<Payment> payments = new ArrayList<>();

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public List<Payment> getAllPayments() {
        return payments;
    }

    public Payment findPaymentByTenantName(String tenantName) {
        if (tenantName == null) return null;

        for (Payment payment : payments) {
            if (tenantName.equals(payment.getTenantName())) {
                return payment;
            }
        }
        return null;
    }

    public boolean updatePaymentAmount(String tenantName, double amount) {
        Payment payment = findPaymentByTenantName(tenantName);

        if (payment != null) {
            payment.setAmount(amount);

            return true;
        }
        return false;
    }

    public void checkDuePayments() {
        for (Payment p : payments) {
            if (p.getStatus().equals("PENDING")) {
                System.out.println(p.getTenantName() + " | " + p.getAmount() + " | Due: " + p.getDueDate());
            }
        }
    }

    public void markAsPaid(Long id) {
        for (Payment p : payments) {
            if (p.getId().equals(id)) {
                p.setStatus("PAID");
                System.out.println("Payment marked as PAID");
            }
        }
    }
}