package com.nestify.pg.service;

import com.nestify.pg.entity.Complaint;
import java.util.ArrayList;
import java.util.List;

public class ComplaintService {

    private List<Complaint> complaints = new ArrayList<>();

    public void addComplaint(Complaint complaint) {
        complaints.add(complaint);
    }

    public List<Complaint> getAllComplaints() {
        return complaints;
    }

    public Complaint findComplaintByTenantName(String tenantName) {
        if (tenantName == null) return null;

        for (Complaint complaint : complaints) {
            if (tenantName.equals(complaint.getTenantName())) {
                return complaint;
            }
        }
        return null;
    }

    public boolean updateComplaintStatus(String tenantName, String status) {
        Complaint complaint = findComplaintByTenantName(tenantName);

        if (complaint != null) {
            complaint.setStatus(status);
            return true;
        }
        return false;
    }
}