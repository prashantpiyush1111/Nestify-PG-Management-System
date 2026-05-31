package com.nestify.pg.service;

import com.nestify.pg.entity.Tenant;
import com.nestify.pg.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public Tenant addTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Tenant updateTenant(Long id, Tenant updated) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
        tenant.setName(updated.getName());
        tenant.setPhoneNumber(updated.getPhoneNumber());
        tenant.setIdProof(updated.getIdProof());
        tenant.setAssignedRoomNumber(updated.getAssignedRoomNumber());
        return tenantRepository.save(tenant);
    }

    public void deleteTenant(Long id) {
        tenantRepository.deleteById(id);
    }
}