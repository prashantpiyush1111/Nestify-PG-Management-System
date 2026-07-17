package com.nestify.pg.service;

import com.nestify.pg.entity.Payment;
import com.nestify.pg.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment addPayment(Payment payment) {
        payment.setPaymentDate(LocalDate.now().toString());
        payment.setStatus("PENDING");
        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsByTenant(String tenantName) {
        return paymentRepository.findByTenantName(tenantName);
    }

    public Payment markAsPaid(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus("PAID");
        return paymentRepository.save(payment);
    }

    public List<Payment> getPendingPayments() {
        return paymentRepository.findByStatus("PENDING");
    }

    public List<Payment> getPendingPaymentsByTenant(String tenantName) {
        return paymentRepository.findByTenantNameAndStatus(tenantName, "PENDING");
    }
}