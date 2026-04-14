package com.nestify.pg;

import java.util.Scanner;

import com.nestify.pg.entity.Room;
import com.nestify.pg.entity.Tenant;
import com.nestify.pg.entity.Complaint;

import com.nestify.pg.service.RoomService;
import com.nestify.pg.service.TenantService;
import com.nestify.pg.service.ComplaintService;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        RoomService roomService = new RoomService();
        TenantService tenantService = new TenantService();
        ComplaintService complaintService = new ComplaintService();

        while (true) {
            System.out.println("\n==== PG MANAGEMENT ====");
            System.out.println("1. Add Room");
            System.out.println("2. Add Tenant");
            System.out.println("3. Add Complaint");
            System.out.println("4. View Rooms");
            System.out.println("5. Exit");
            System.out.println("6. Assign Room");
            System.out.println("7. View Tenants");
            System.out.println("8. View Complaints");
            System.out.println("9. View Payments");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Room No: ");
                    String roomNo = sc.nextLine();

                    System.out.print("Type: ");
                    String type = sc.nextLine();

                    System.out.print("Rent: ");
                    double rent = sc.nextDouble();

                    Room room = new Room(0L, roomNo, type, rent, true);
                    roomService.addRoom(room);
                    break;

                case 2:
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Phone: ");
                    String phone = sc.nextLine();

                    System.out.print("ID Proof: ");
                    String idProof = sc.nextLine();

                    System.out.print("Room No: ");
                    String rNo = sc.nextLine();

                    Tenant tenant = new Tenant(0L, name, phone, idProof, rNo);
                    tenantService.addTenant(tenant);
                    break;

                case 3:
                    System.out.print("Tenant Name: ");
                    String tName = sc.nextLine();

                    System.out.print("Room No: ");
                    String rn = sc.nextLine();

                    System.out.print("Issue: ");
                    String issue = sc.nextLine();

                    Complaint complaint = new Complaint(0L, tName, rn, issue, "Pending");
                    complaintService.addComplaint(complaint);
                    break;

                case 4:
                    roomService.getAllRooms().forEach(r ->
                        System.out.println(r.getRoomNumber() + " - " + r.getRoomType())
                    );
                    break;

                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                case 6:
                    System.out.print("Room ID: ");
                    int roomId = sc.nextInt();

                    System.out.print("Tenant ID: ");
                    int tenantId = sc.nextInt();

                    roomService.assignRoomToTenant(roomId, tenantId);
                    break;
                    
                case 7:
                    tenantService.getAllTenants().forEach(t ->
                        System.out.println(t.getName() + " - " + t.getAssignedRoomNumber())
                    );
                    break;
                case 8:
                    complaintService.getAllComplaints().forEach(c ->
                        System.out.println(c.getTenantName() + " - " + c.getIssue() + " - " + c.getStatus())
                    );
                    break;
                case 9:
                    PaymentService.getAllPayments().forEach(p ->
                        System.out.println(p.getTenantName() + " - " + p.getAmount() + " - " + p.getDate())
                    );
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}