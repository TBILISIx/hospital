package com.solvd.hospital;

public class Room {

    public enum RoomType {
        ICU, GENERAL, SURGERY, RECOVERY, PRIVATE
    }

    private Long id;
    private String roomNumber;
    private RoomType type;
    private Long capacity;
    private boolean occupied;
    private Long departmentId;

    public Room() {
    }

    public Room(String roomNumber, RoomType type, Long capacity) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
    }

    public Room(Long id, String roomNumber, RoomType type, Long capacity, boolean occupied, Long departmentId) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
        this.occupied = occupied;
        this.departmentId = departmentId;
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

    public Long getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Room{id=" + id + ", roomNumber='" + roomNumber + "', type=" + type +
                ", capacity=" + capacity + ", occupied=" + occupied +
                ", departmentId=" + departmentId + "}";
    }

}
