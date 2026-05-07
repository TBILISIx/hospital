package com.solvd.hospital;

import java.util.ArrayList;
import java.util.List;

public class Doctor {

    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private boolean available;
    private Department department;
    private List<Appointment> appointments = new ArrayList<>();

    public Doctor(String firstName, String lastName, String specialization, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.department = department;
    }

    public Doctor(Long id, String firstName, String lastName, String specialization, boolean available, Department department, List<Appointment> appointments) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.available = available;
        this.department = department;
        this.appointments = appointments;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization='" + specialization + '\'' +
                ", available=" + available +
                ", department=" + department +
                ", appointments=" + appointments +
                '}';
    }

}
