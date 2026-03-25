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
}