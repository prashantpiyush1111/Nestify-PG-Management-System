package com.nestify.pg.dao;

import com.nestify.pg.entity.Payment;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private List<Payment> payments = new ArrayList<>();

    // Add Payment
    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    // Get All Payments
    public List<Payment> getAllPayments() {
        return payments;
    }

    // Get Payments by Tenant ID
    public List<Payment> getPaymentsByTenantName(String tenantName) {
        List<Payment> result = new ArrayList<>();

        for (Payment p : payments) {
            if (p.getTenantName().equals(tenantName)) {
                result.add(p);
            }
        }

        return result;
    }
}