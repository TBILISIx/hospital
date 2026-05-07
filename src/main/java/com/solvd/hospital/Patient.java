package com.solvd.hospital;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patient {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private boolean insured;
    private List<Appointment> appointments = new ArrayList<>();
    private MedicalRecord medicalRecord;
    private List<Prescription> prescriptions = new ArrayList<>();

    public Patient() {
    }

    public Patient(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, boolean insured) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.insured = insured;
    }

    public Patient(Long id, String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, boolean insured, List<Appointment> appointments, MedicalRecord medicalRecord, List<Prescription> prescriptions) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.insured = insured;
        this.appointments = appointments;
        this.medicalRecord = medicalRecord;
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
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public boolean isInsured() {
        return insured;
    }
    public void setInsured(boolean insured) {
        this.insured = insured;
    }
    public List<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }
    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }
    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", insured=" + insured +
                ", appointments=" + appointments +
                ", medicalRecord=" + medicalRecord +
                ", prescriptions=" + prescriptions +
                '}';

    }

}