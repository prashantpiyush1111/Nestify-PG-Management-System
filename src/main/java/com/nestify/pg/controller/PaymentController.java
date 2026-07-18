package com.nestify.pg.controller;

import com.nestify.pg.entity.Payment;
import com.nestify.pg.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_ADMIN"));
    }

    @GetMapping
    public List<Payment> getAllPayments(Authentication auth) {
        if (isAdmin(auth)) {
            return paymentService.getAllPayments();
        }
        return paymentService.getPaymentsByTenant(auth.getName());
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
    public List<Payment> getPending(Authentication auth) {
        if (isAdmin(auth)) {
            return paymentService.getPendingPayments();
        }
        return paymentService.getPendingPaymentsByTenant(auth.getName());
    }

    @GetMapping("/tenant/{name}")
    public List<Payment> getByTenant(@PathVariable String name, Authentication auth) {
        if (!isAdmin(auth) && !auth.getName().equals(name)) {
            throw new AccessDeniedException("You can only view your own payments");
        }
        return paymentService.getPaymentsByTenant(name);
    }
}