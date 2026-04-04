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
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO tenant (id, name, phone, idProof, roomNumber) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setLong(1, tenant.getId());
            ps.setString(2, tenant.getName());
            ps.setString(3, tenant.getPhoneNumber());
            ps.setString(4, tenant.getIdProof());
            ps.setString(5, tenant.getAssignedRoomNumber());

            ps.executeUpdate();

            System.out.println("Tenant saved in DB");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SELECT
    public List<Tenant> getAllTenants() {
        List<Tenant> tenants = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM tenant";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Tenant t = new Tenant(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("idProof"),
                        rs.getString("roomNumber")
                );
                tenants.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tenants;
    }
}