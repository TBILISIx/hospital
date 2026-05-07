package com.solvd.hospital;

import java.util.ArrayList;
import java.util.List;

public class Department {

    private Long id;
    private String name;             // "Cardiology", "Emergency", etc.
    private String location;         // "Building A, Floor 2"
    private List<Doctor> doctors = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Staff> staff = new ArrayList<>();

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Department(Long id, String name, String location, List<Doctor> doctors, List<Room> rooms, List<Staff> staff) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.doctors = doctors;
        this.rooms = rooms;
        this.staff = staff;
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
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public List<Doctor> getDoctors() {
        return doctors;
    }
    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
    public List<Room> getRooms() {
        return rooms;
    }
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
    public List<Staff> getStaff() {
        return staff;
    }
    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

}