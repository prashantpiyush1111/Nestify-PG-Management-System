package com.nestify.pg.repository;

import com.nestify.pg.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByTenantName(String tenantName);
    List<Payment> findByStatus(String status);
    List<Payment> findByTenantNameAndStatus(String tenantName, String status);
}
