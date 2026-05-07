package com.solvd.hospital;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prescription {

    private Long id;
    private Patient patient;
    private Doctor doctor;
    private LocalDate issuedDate;
    private List<String> medications = new ArrayList<>();
    private String instructions;

    public Prescription() {
    }

    public Prescription(Patient patient, Doctor doctor, LocalDate issuedDate) {
        this.patient = patient;
        this.doctor = doctor;
        this.issuedDate = issuedDate;
    }

    public Prescription(String instructions, List<String> medications, LocalDate issuedDate, Doctor doctor, Patient patient, Long id) {
        this.instructions = instructions;
        this.medications = medications;
        this.issuedDate = issuedDate;
        this.doctor = doctor;
        this.patient = patient;
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }
    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public List<String> getMedications() {
        return medications;
    }
    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

}
