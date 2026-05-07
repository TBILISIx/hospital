package com.solvd.hospital.enums;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MedicalClinic {

    private Integer id;
    private String name;
    private String address;
    private Integer totalPatientRooms;
    private Integer occupiedPatientRooms;
    private List<Department> departments = new ArrayList<>();

    public MedicalClinic() {
    }

    public MedicalClinic(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public MedicalClinic(Integer id, String name, String address, Integer totalPatientRooms, Integer occupiedPatientRooms, List<Department> departments) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.totalPatientRooms = totalPatientRooms;
        this.occupiedPatientRooms = occupiedPatientRooms;
        this.departments = departments;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getTotalPatientRooms() { return totalPatientRooms; }
    public void setTotalPatientRooms(Integer totalPatientRooms) { this.totalPatientRooms = totalPatientRooms; }

    public Integer getOccupiedPatientRooms() { return occupiedPatientRooms; }
    public void setOccupiedPatientRooms(Integer occupiedPatientRooms) { this.occupiedPatientRooms = occupiedPatientRooms; }

    public List<Department> getDepartments() { return departments; }
    public void setDepartments(List<Department> departments) { this.departments = departments; }

    @Override
    public String toString() {
        return "MedicalClinic{id=" + id + ", name='" + name + "', address='" + address +
                "', totalPatientRooms=" + totalPatientRooms + ", occupiedPatientRooms=" + occupiedPatientRooms +
                ", departments=" + departments + "}";
    }
}
