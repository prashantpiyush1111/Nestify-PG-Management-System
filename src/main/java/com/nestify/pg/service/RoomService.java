package com.nestify.pg.service;

import com.nestify.pg.entity.Room;
import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private List<Room> rooms = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    public Room findRoomByNumber(String roomNumber) {
        if (roomNumber == null) return null;

        for (Room room : rooms) {
            if (roomNumber.equals(room.getRoomNumber())) {
                return room;
            }
        }
        return null;
    }

    public boolean updateRoomRent(String roomNumber, double newRent) {
        Room room = findRoomByNumber(roomNumber);

        if (room != null) {
            room.setRent(newRent);
            return true;
        }
        return false;
    }

    public boolean updateRoomAvailability(String roomNumber, boolean available) {
        Room room = findRoomByNumber(roomNumber);

        if (room != null) {
            room.setAvailable(available);
            return true;
        }
        
        return false;
    }
    public void assignRoomToTenant(int roomId, int tenantId) {
        com.nestify.pg.dao.RoomDAO roomDAO = new com.nestify.pg.dao.RoomDAO();
        roomDAO.assignRoomToTenant(roomId, tenantId);
    }
}
