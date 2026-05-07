package com.solvd.hospital;

public class Room {

    private Long id;
    private String roomNumber;
    private RoomType type;
    private int capacity;
    private boolean occupied;
    private Department department;

    public Room(String roomNumber, RoomType type, int capacity, Department department) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
        this.department = department;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getType() {
        return type;
    }
    public void setType(RoomType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isOccupied() {
        return occupied;
    }
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    public enum RoomType {
        ICU, GENERAL, SURGERY, RECOVERY, PRIVATE
    }

}


