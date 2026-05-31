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
        String query = "INSERT INTO complaint (tenant_name, room_number, issue, status) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, complaint.getTenantName());
            ps.setString(2, complaint.getRoomNumber());
            ps.setString(3, complaint.getIssue());
            ps.setString(4, complaint.getStatus());
            ps.executeUpdate();
            System.out.println("Complaint added successfully");

        } catch (Exception e) {
            System.err.println("Error adding complaint: " + e.getMessage());
        }
    }

    // Get All Complaints
    public List<Complaint> getAllComplaints() {
        List<Complaint> list = new ArrayList<>();
        String query = "SELECT * FROM complaint";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Complaint c = new Complaint(
                    rs.getLong("id"),
                    rs.getString("tenant_name"),
                    rs.getString("room_number"),
                    rs.getString("issue"),
                    rs.getString("status")
                );
                list.add(c);
            }

        } catch (Exception e) {
            System.err.println("Error fetching complaints: " + e.getMessage());
        }

        return list;
    }

    // Update Complaint Status
    public void updateComplaintStatus(long complaintId, String status) {
        String query = "UPDATE complaint SET status = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setLong(2, complaintId);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Complaint status updated");
            } else {
                System.out.println("Complaint not found with id: " + complaintId);
            }

        } catch (Exception e) {
            System.err.println("Error updating complaint: " + e.getMessage());
        }
    }

    // Delete Complaint
    public void deleteComplaint(long id) {
        String query = "DELETE FROM complaint WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setLong(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Complaint deleted successfully");
            } else {
                System.out.println("Complaint not found with id: " + id);
            }

        } catch (Exception e) {
            System.err.println("Error deleting complaint: " + e.getMessage());
        }
    }
}