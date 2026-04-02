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
            payment = new Payment(
                payment.getId(),
                payment.getTenantName(),
                payment.getRoomNumber(),
                amount,
                payment.getPaymentDate()
            );
            return true;
        }
        return false;
    }
}