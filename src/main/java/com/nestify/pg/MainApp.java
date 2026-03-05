package com.nestify.pg;

import com.nestify.pg.entity.Room;
import com.nestify.pg.entity.Tenant;
import com.nestify.pg.service.RoomService;
import com.nestify.pg.service.TenantService;

public class MainApp {

    public static void main(String[] args) {

        RoomService roomService = new RoomService();
        TenantService tenantService = new TenantService();

        Room room = new Room(1L, "101", "Single", 5000, true);
        roomService.addRoom(room);

        Tenant tenant = new Tenant(1L, "Rahul", "9876543210", "Aadhar", "101");
        tenantService.addTenant(tenant);

        System.out.println("Room Added: " + room.getRoomNumber());
        System.out.println("Tenant Added: " + tenant.getName());
    }
}