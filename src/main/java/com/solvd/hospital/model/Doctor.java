package com.solvd.hospital.model;

import java.util.List;

public class Doctor {

    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private boolean available;
    private List<Appointment> appointments;
    private List<Prescription> prescriptions;
    private Long departmentId; // needed for DB mapping


    public Doctor() {

    }

    public Doctor(Long id, String firstName, String lastName, String specialization,
                  boolean available, List<Appointment> appointments, List<Prescription> prescriptions) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.available = available;
        this.appointments = appointments;
        this.prescriptions = prescriptions;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Doctor{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName +
                "', specialization='" + specialization + "', available=" + available +
                ", departmentId=" + departmentId + "}";
    }

}
