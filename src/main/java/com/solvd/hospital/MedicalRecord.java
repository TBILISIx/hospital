package com.solvd.hospital;

import com.solvd.hospital.enums.BloodType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {

    private Long id;
    private Patient patient;
    private LocalDate createdDate;
    private BloodType bloodType;
    private List<String> allergies = new ArrayList<>();
    private String notes;

    public MedicalRecord() {
    }

    public MedicalRecord(Patient patient, LocalDate createdDate, BloodType bloodType) {
        this.patient = patient;
        this.createdDate = createdDate;
        this.bloodType = bloodType;
    }

    public MedicalRecord(Long id, Patient patient, LocalDate createdDate, BloodType bloodType, List<String> allergies, String notes) {
        this.id = id;
        this.patient = patient;
        this.createdDate = createdDate;
        this.bloodType = bloodType;
        this.allergies = allergies;
        this.notes = notes;
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

    public LocalDate getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public BloodType getBloodType() {
        return bloodType;
    }
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public List<String> getAllergies() {
        return allergies;
    }
    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "id=" + id +
                ", patient=" + patient +
                ", createdDate=" + createdDate +
                ", bloodType=" + bloodType +
                ", allergies=" + allergies +
                ", notes='" + notes + '\'' +
                '}';
    }

}