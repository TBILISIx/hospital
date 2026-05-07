package com.solvd.hospital;

public class Room {

    public enum RoomType {
        ICU, GENERAL, SURGERY, RECOVERY, PRIVATE
    }

    private Integer id;
    private String roomNumber;
    private RoomType type;
    private int capacity;
    private boolean occupied;

    public Room() {
    }

    public Room(String roomNumber, RoomType type, int capacity) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
    }

    public Room(Integer id, String roomNumber, RoomType type, int capacity, boolean occupied) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.type = type;
        this.capacity = capacity;
        this.occupied = occupied;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public RoomType getType() { return type; }
    public void setType(RoomType type) { this.type = type; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }

    @Override
    public String toString() {
        return "Room{id=" + id + ", roomNumber='" + roomNumber + "', type=" + type +
                ", capacity=" + capacity + ", occupied=" + occupied + "}";
    }
}
