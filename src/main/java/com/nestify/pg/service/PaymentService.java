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
}