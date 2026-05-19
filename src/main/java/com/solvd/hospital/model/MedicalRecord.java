package com.solvd.hospital.model;

import java.time.LocalDate;
import java.util.List;

public class MedicalRecord {

    private Long id;
    private LocalDate createdDate;
    private BloodType bloodType;
    private List<Allergy> allergies;
    private String notes;

    public MedicalRecord(Long id, LocalDate createdDate, BloodType bloodType, List<Allergy> allergies, String notes) {
        this.id = id;
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

    public List<Allergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Allergy> allergies) {
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
        return "MedicalRecord{id=" + id +
                ", createdDate=" + createdDate + ", bloodType=" + bloodType +
                ", allergies=" + allergies + ", notes='" + notes + "'}";
    }

}
