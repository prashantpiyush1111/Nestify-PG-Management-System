package com.nestify.pg;

import java.sql.Connection;
import java.util.List;

import com.nestify.pg.dao.TenantDAO;
import com.nestify.pg.entity.Room;
import com.nestify.pg.entity.Tenant;
import com.nestify.pg.entity.Payment;
import com.nestify.pg.entity.Complaint;

import com.nestify.pg.service.RoomService;
import com.nestify.pg.service.TenantService;
import com.nestify.pg.service.PaymentService;
import com.nestify.pg.service.ComplaintService;

import com.nestify.pg.util.DBConnection;

public class MainApp {

    public static void main(String[] args) {

        // STEP 1: DB Connection Test
        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            System.out.println(" Database Connected");
        } else {
            System.out.println(" Database Connection Failed");
            return; //  ADDED: stop execution if DB fails
        }

        // STEP 2: Services initialize
        RoomService roomService = new RoomService();
        TenantService tenantService = new TenantService();
        PaymentService paymentService = new PaymentService();
        ComplaintService complaintService = new ComplaintService();

        // STEP 3: Add Room
        Room room = new Room(1L, "101", "Single", 5000, true);
        roomService.addRoom(room);

        // STEP 4: Add Tenant
        Tenant tenant = new Tenant(1L, "Prashant", "9876543210", "Aadhar", "101");
        tenantService.addTenant(tenant);

        // STEP 5: Add Payment
        Payment payment = new Payment(1L, "Prashant", "101", 5000, "10 March");
        paymentService.addPayment(payment);

        // STEP 6: Add Complaint
        Complaint complaint = new Complaint(1L, "Prashant", "101", "Fan not working", "Pending");
        complaintService.addComplaint(complaint);

        // STEP 7: Display Output
        System.out.println("Room Added: " + room.getRoomNumber());
        System.out.println("Tenant Added: " + tenant.getName());
        System.out.println("Payment Done by: " + payment.getTenantName());
        System.out.println("Complaint: " + complaint.getIssue());

        // STEP 8: Search Room
        Room foundRoom = roomService.findRoomByNumber("101");
        if (foundRoom != null) {
            System.out.println("Room Found: " + foundRoom.getRoomNumber());
        } else {
            System.out.println("Room not found");
        }

        // STEP 9: Search Tenant
        Tenant foundTenant = tenantService.findTenantByName("Prashant");
        if (foundTenant != null) {
            System.out.println("Tenant Found: " + foundTenant.getName());
        } else {
            System.out.println("Tenant not found");
        }

        // STEP 10: Update Room Rent
        boolean updatedRoom = roomService.updateRoomRent("101", 6000);
        if (updatedRoom) {
            System.out.println("Room rent updated");
        }

        // STEP 11: Update Tenant Room
        boolean updatedTenant = tenantService.updateTenantRoom("Prashant", "102");
        if (updatedTenant) {
            System.out.println("Tenant room updated");
        }

        //  ADDED: verify updated tenant room
        Tenant updated = tenantService.findTenantByName("Prashant");
        if (updated != null) {
            System.out.println("Updated Room: " + updated.getAssignedRoomNumber());
        }

        // STEP 12: DAO (Database Operations)
        TenantDAO tenantDAO = new TenantDAO();

        // Save to DB
        tenantDAO.saveTenant(tenant);

        // Fetch from DB
        List<Tenant> list = tenantDAO.getAllTenants();

        for (Tenant t : list) {
            System.out.println(t.getName() + " - " + t.getAssignedRoomNumber());
        }
    }
}