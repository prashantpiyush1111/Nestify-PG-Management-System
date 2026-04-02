package com.nestify.pg.entity;

public class Tenant {

    private Long id;
    private String name;
    private String phoneNumber;
    private String idProof;
    private String assignedRoomNumber;

    public Tenant() {
    }

    public Tenant(Long id, String name, String phoneNumber, String idProof, String assignedRoomNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.idProof = idProof;
        this.assignedRoomNumber = assignedRoomNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getIdProof() {
        return idProof;
    }

    public String getAssignedRoomNumber() {
        return assignedRoomNumber;
    }

    public void setAssignedRoomNumber(String assignedRoomNumber) {
        this.assignedRoomNumber = assignedRoomNumber;
    }

    //  fix for service compatibility
    public void setRoom(String roomNumber) {
        this.assignedRoomNumber = roomNumber;
    }

    public String getRoom() {
        return this.assignedRoomNumber;
    }
}