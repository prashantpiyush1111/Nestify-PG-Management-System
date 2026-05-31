package com.nestify.pg.repository;

import com.nestify.pg.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    List<Tenant> findByAssignedRoomNumber(String roomNumber);
}