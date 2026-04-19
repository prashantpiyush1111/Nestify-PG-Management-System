package com.nestify.pg.service;

import com.nestify.pg.entity.Complaint;
import com.nestify.pg.dao.ComplaintDAO;
import java.util.List;

public class ComplaintService {

    private ComplaintDAO dao = new ComplaintDAO();

    // Add Complaint (DB me save karega)
    public void addComplaint(Complaint complaint) {
        dao.addComplaint(complaint);
    }

    // Get All Complaints (DB se fetch karega)
    public List<Complaint> getAllComplaints() {
        return dao.getAllComplaints();
    }

    // Update Complaint Status (by ID)
    public void updateComplaintStatus(int id, String status) {
        dao.updateComplaintStatus(id, status);
    }

    // Delete Complaint (by ID)
    public void deleteComplaint(int id) {
        dao.deleteComplaint(id);
    }
}