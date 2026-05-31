package com.nestify.pg.controller;

import com.nestify.pg.entity.Payment;
import com.nestify.pg.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @PostMapping
    public ResponseEntity<Payment> addPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.addPayment(payment));
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<Payment> markAsPaid(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.markAsPaid(id));
    }

    @GetMapping("/pending")
    public List<Payment> getPending() {
        return paymentService.getPendingPayments();
    }

    @GetMapping("/tenant/{name}")
    public List<Payment> getByTenant(@PathVariable String name) {
        return paymentService.getPaymentsByTenant(name);
    }
}