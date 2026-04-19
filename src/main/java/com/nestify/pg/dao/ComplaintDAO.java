package com.nestify.pg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.nestify.pg.entity.Complaint;
import com.nestify.pg.util.DBConnection;

public class ComplaintDAO {

    // Add Complaint 
    public void addComplaint(Complaint complaint) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO complaint (tenant_name, room_number, description, status) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, complaint.getTenantName());
            ps.setString(2, complaint.getRoomNumber());
            ps.setString(3, complaint.getIssue());
            ps.setString(4, complaint.getStatus());

            ps.executeUpdate();
            System.out.println("Complaint added successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get All Complaints
    public List<Complaint> getAllComplaints() {
        List<Complaint> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM complaint";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Complaint c = new Complaint(
                    (long) rs.getInt("id"),
                    rs.getString("tenant_name"),
                    rs.getString("room_number"),
                    rs.getString("description"),
                    rs.getString("status")
                );

                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Update Complaint Status
    public void updateComplaintStatus(int complaintId, String status) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "UPDATE complaint SET status = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, status);
            ps.setInt(2, complaintId);

            ps.executeUpdate();
            System.out.println("Complaint status updated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteComplaint(int id) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "DELETE FROM complaint WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Complaint deleted successfully");
            } else {
                System.out.println("Complaint not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}