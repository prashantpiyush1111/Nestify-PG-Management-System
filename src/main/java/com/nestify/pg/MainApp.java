package com.nestify.pg;

import com.nestify.pg.entity.Room;
import com.nestify.pg.entity.Tenant;
import com.nestify.pg.entity.Payment;
import com.nestify.pg.entity.Complaint;

import com.nestify.pg.service.RoomService;
import com.nestify.pg.service.TenantService;
import com.nestify.pg.service.PaymentService;
import com.nestify.pg.service.ComplaintService;

public class MainApp {

    public static void main(String[] args) {

        RoomService roomService = new RoomService();
        TenantService tenantService = new TenantService();
        PaymentService paymentService = new PaymentService();
        ComplaintService complaintService = new ComplaintService();

        // Room
        Room room = new Room(1L, "101", "Single", 5000, true);
        roomService.addRoom(room);

        // Tenant
        Tenant tenant = new Tenant(1L, "Prashant ", "9876543210", "Aadhar", "101");
        tenantService.addTenant(tenant);

        // Payment
        Payment payment = new Payment(1L, "Prashant ", "101", 5000, "10 March");
        paymentService.addPayment(payment);

        // Complaint
        Complaint complaint = new Complaint(1L, "Prashant ", "101", "Fan not working", "Pending");
        complaintService.addComplaint(complaint);

        // Output
        System.out.println("Room Added: " + room.getRoomNumber());
        System.out.println("Tenant Added: " + tenant.getName());
        System.out.println("Payment Done by: " + payment.getTenantName());
        System.out.println("Complaint: " + complaint.getIssue());
    }
}