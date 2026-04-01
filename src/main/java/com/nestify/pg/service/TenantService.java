package com.nestify.pg.service;

import com.nestify.pg.entity.Tenant;
import java.util.ArrayList;
import java.util.List;

public class TenantService {

    private List<Tenant> tenants = new ArrayList<>();

    public void addTenant(Tenant tenant) {
        tenants.add(tenant);
    }

    public List<Tenant> getAllTenants() {
        return tenants;
    }

    public Tenant findTenantByName(String name) {
        if (name == null) return null;

        for (Tenant tenant : tenants) {
            if (name.equals(tenant.getName())) {
                return tenant;
            }
        }
        return null;
    }
}