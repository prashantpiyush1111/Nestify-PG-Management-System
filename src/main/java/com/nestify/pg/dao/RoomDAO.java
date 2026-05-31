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
        String query = "INSERT INTO room (room_number, room_type, rent, available) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, room.getRoomNumber());
            ps.setString(2, room.getRoomType());
            ps.setDouble(3, room.getRent());
            ps.setBoolean(4, room.isAvailable());

            ps.executeUpdate();
            System.out.println("Room added successfully");

        } catch (Exception e) {
            System.err.println("Error adding room: " + e.getMessage());
        }
    }

    // Get All Rooms
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM room";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getLong("id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setRoomType(rs.getString("room_type"));
                room.setRent(rs.getDouble("rent"));
                room.setAvailable(rs.getBoolean("available"));
                rooms.add(room);
            }

        } catch (Exception e) {
            System.err.println("Error fetching rooms: " + e.getMessage());
        }

        return rooms;
    }

    // Assign Room to Tenant
    public void assignRoomToTenant(int roomId, int tenantId) {
        String roomQuery = "UPDATE room SET available = false WHERE id = ?";
        String tenantQuery = "UPDATE tenant SET room_number = (SELECT room_number FROM room WHERE id = ?) WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps1 = con.prepareStatement(roomQuery);
             PreparedStatement ps2 = con.prepareStatement(tenantQuery)) {

            ps1.setInt(1, roomId);
            ps1.executeUpdate();

            ps2.setInt(1, roomId);
            ps2.setInt(2, tenantId);
            ps2.executeUpdate();

            System.out.println("Room assigned successfully");

        } catch (Exception e) {
            System.err.println("Error assigning room: " + e.getMessage());
        }
    }
}