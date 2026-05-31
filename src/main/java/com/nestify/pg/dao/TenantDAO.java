package com.nestify.pg.dao;

import com.nestify.pg.entity.Tenant;
import com.nestify.pg.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TenantDAO {

    // INSERT
    public void saveTenant(Tenant tenant) {
        String sql = "INSERT INTO tenant (name, phone_number, id_proof, room_number) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenant.getName());
            ps.setString(2, tenant.getPhoneNumber());
            ps.setString(3, tenant.getIdProof());
            ps.setString(4, tenant.getAssignedRoomNumber());
            ps.executeUpdate();
            System.out.println("Tenant saved in DB");

        } catch (Exception e) {
            System.err.println("Error saving tenant: " + e.getMessage());
        }
    }

    // SELECT
    public List<Tenant> getAllTenants() {
        List<Tenant> tenants = new ArrayList<>();
        String sql = "SELECT * FROM tenant";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tenant t = new Tenant(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("phone_number"),
                        rs.getString("id_proof"),
                        rs.getString("room_number")
                );
                tenants.add(t);
            }

        } catch (Exception e) {
            System.err.println("Error fetching tenants: " + e.getMessage());
        }

        return tenants;
    }
}