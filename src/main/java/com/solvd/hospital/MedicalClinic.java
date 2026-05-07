package com.solvd.hospital;

import java.util.ArrayList;
import java.util.List;

public class MedicalClinic {

    private Long id;
    private String name;
    private String address;
    private Long totalPatientRooms;
    private Long occupiedPatientRooms;
    private List<Department> departments = new ArrayList<>();

    public MedicalClinic() {
    }

    public MedicalClinic(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public MedicalClinic(Long id, String name, String address, Long totalPatientRooms, Long occupiedPatientRooms, List<Department> departments) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.totalPatientRooms = totalPatientRooms;
        this.occupiedPatientRooms = occupiedPatientRooms;
        this.departments = departments;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Long getTotalPatientRooms() { return totalPatientRooms; }
    public void setTotalPatientRooms(Long totalPatientRooms) { this.totalPatientRooms = totalPatientRooms; }

    public Long getOccupiedPatientRooms() { return occupiedPatientRooms; }
    public void setOccupiedPatientRooms(Long occupiedPatientRooms) { this.occupiedPatientRooms = occupiedPatientRooms; }

    public List<Department> getDepartments() { return departments; }
    public void setDepartments(List<Department> departments) { this.departments = departments; }

    @Override
    public String toString() {
        return "MedicalClinic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", totalPatientRooms=" + totalPatientRooms +
                ", occupiedPatientRooms=" + occupiedPatientRooms +
                ", departments=" + departments +
                '}';
    }

}
