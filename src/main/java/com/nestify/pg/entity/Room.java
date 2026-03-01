package com.nestify.pg.entity;

public class Room {

    private Long id;
    private String roomNumber;
    private String roomType;
    private double rent;
    private boolean available;

    public Room() {
    }

    public Room(Long id, String roomNumber, String roomType, double rent, boolean available) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.rent = rent;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}