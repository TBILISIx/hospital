package com.solvd.hospital.model;

import java.util.List;

public class Room {

    public enum RoomType {
        ICU, GENERAL, SURGERY, RECOVERY, PRIVATE
    }

    private Long id;
    private String roomNumber;
    private RoomType type;
    private Long capacity;
    private boolean occupied;
    private List<Admission> admissions;

    public Room(Long id, String roomNumber, RoomType type, Long capacity, boolean occupied, List<Admission> admissions) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
        this.occupied = occupied;
        this.admissions = admissions;
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

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public List<Admission> getAdmissions() {
        return admissions;
    }

    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber='" + roomNumber + '\'' +
                ", type=" + type +
                ", capacity=" + capacity +
                ", occupied=" + occupied +
                ", admissions=" + admissions +
                '}';
    }

}
