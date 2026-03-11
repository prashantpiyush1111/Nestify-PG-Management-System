package com.nestify.pg;

import com.nestify.pg.entity.Room;
import com.nestify.pg.entity.Tenant;
import com.nestify.pg.entity.Payment;
import com.nestify.pg.service.RoomService;
import com.nestify.pg.service.TenantService;
import com.nestify.pg.service.PaymentService;

public class MainApp {

    public static void main(String[] args) {

        RoomService roomService = new RoomService();
        TenantService tenantService = new TenantService();
        PaymentService paymentService = new PaymentService();

        Room room = new Room(1L, "101", "Single", 5000, true);
        roomService.addRoom(room);

        Tenant tenant = new Tenant(1L, "Rahul", "9876543210", "Aadhar", "101");
        tenantService.addTenant(tenant);

        Payment payment = new Payment(1L, "Rahul", "101", 5000, "10 March");
        paymentService.addPayment(payment);

        System.out.println("Room Added: " + room.getRoomNumber());
        System.out.println("Tenant Added: " + tenant.getName());
        System.out.println("Payment Done by: " + payment.getTenantName());
    }
}