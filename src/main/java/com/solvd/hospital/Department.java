package com.solvd.hospital;

import java.util.List;

public class Department {

    private Long id;
    private String name;
    private String location;
    private List<Doctor> doctors;
    private List<Staff> staff;
    private List<Room> rooms;

    public Department(Long id, String name, String location, List<Doctor> doctors, List<Staff> staff, List<Room> rooms) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.doctors = doctors;
        this.staff = staff;
        this.rooms = rooms;
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

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Department{id=" + id + ", name='" + name + "', location='" + location +
                "', doctors=" + doctors + ", staff=" + staff + ", rooms=" + rooms + "}";
    }

}
