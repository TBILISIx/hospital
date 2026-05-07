package com.solvd.hospital;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;



@Slf4j

public class MedicalClinic {

    private String name;
    private String address;
    private Integer totalPatientRooms;
    private Integer occupiedPatientRooms;
    private List<Department> departments = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private List<Admission> admissions = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();

    public MedicalClinic() {
    }


    public MedicalClinic(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public MedicalClinic(String name, String address, Integer totalPatientRooms, Integer occupiedPatientRooms, List<Department> departments, List<Patient> patients, List<Appointment> appointments, List<Admission> admissions, List<Payment> payments) {
        this.name = name;
        this.address = address;
        this.totalPatientRooms = totalPatientRooms;
        this.occupiedPatientRooms = occupiedPatientRooms;
        this.departments = departments;
        this.patients = patients;
        this.appointments = appointments;
        this.admissions = admissions;
        this.payments = payments;
    }

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

    public List<Patient> getPatients() { return patients; }
    public void setPatients(List<Patient> patients) { this.patients = patients; }

    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }

    public List<Admission> getAdmissions() { return admissions; }
    public void setAdmissions(List<Admission> admissions) { this.admissions = admissions; }

    public List<Payment> getPayments() { return payments; }
    public void setPayments(List<Payment> payments) { this.payments = payments; }

    @Override
    public String toString() {
        return "MedicalClinic{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", totalPatientRooms=" + totalPatientRooms +
                ", occupiedPatientRooms=" + occupiedPatientRooms +
                ", departments=" + departments +
                ", patients=" + patients +
                ", appointments=" + appointments +
                ", admissions=" + admissions +
                ", payments=" + payments +
                '}';
    }

}
