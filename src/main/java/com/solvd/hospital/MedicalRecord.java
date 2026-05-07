package com.solvd.hospital;

import com.solvd.hospital.enums.BloodType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {

    private Integer id;
    private LocalDate createdDate;
    private BloodType bloodType;
    private List<Allergy> allergies = new ArrayList<>();
    private String notes;

    public MedicalRecord() {
    }

    public MedicalRecord(LocalDate createdDate, BloodType bloodType) {
        this.createdDate = createdDate;
        this.bloodType = bloodType;
    }

    public MedicalRecord(Integer id, LocalDate createdDate, BloodType bloodType, List<Allergy> allergies, String notes) {
        this.id = id;
        this.createdDate = createdDate;
        this.bloodType = bloodType;
        this.allergies = allergies;
        this.notes = notes;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }

    public BloodType getBloodType() { return bloodType; }
    public void setBloodType(BloodType bloodType) { this.bloodType = bloodType; }

    public List<Allergy> getAllergies() { return allergies; }
    public void setAllergies(List<Allergy> allergies) { this.allergies = allergies; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "MedicalRecord{id=" + id + ", createdDate=" + createdDate +
                ", bloodType=" + bloodType + ", allergies=" + allergies + ", notes='" + notes + "'}";
    }
}
