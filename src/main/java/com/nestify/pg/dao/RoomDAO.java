package com.nestify.pg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.nestify.pg.entity.Room;
import com.nestify.pg.util.DBConnection;

public class RoomDAO {

    // Add Room
    public void addRoom(Room room) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO room (room_no, type, rent, status) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, room.getRoomNumber());
            ps.setString(2, room.getRoomType());
            ps.setDouble(3, room.getRent());
            ps.setString(4, room.isAvailable() ? "Available" : "Occupied");

            ps.executeUpdate();
            System.out.println("Room added successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get All Rooms
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM room";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Room room = new Room();
                room.setId((long) rs.getInt("id"));
                room.setRoomNumber(rs.getString("room_no"));
                room.setRoomType(rs.getString("type"));
                room.setRent(rs.getDouble("rent"));
                room.setAvailable("Available".equals(rs.getString("status")));

                rooms.add(room);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rooms;
    }

    // Assign Room to Tenant
    public void assignRoomToTenant(int roomId, int tenantId) {
        try {
            Connection con = DBConnection.getConnection();

            String roomQuery = "UPDATE room SET status = 'Occupied' WHERE id = ?";
            PreparedStatement ps1 = con.prepareStatement(roomQuery);
            ps1.setInt(1, roomId);
            ps1.executeUpdate();

            String tenantQuery = "UPDATE tenant SET room_id = ? WHERE id = ?";
            PreparedStatement ps2 = con.prepareStatement(tenantQuery);
            ps2.setInt(1, roomId);
            ps2.setInt(2, tenantId);
            ps2.executeUpdate();

            System.out.println("Room assigned successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}