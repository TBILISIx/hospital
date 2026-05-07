package com.solvd.hospital;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prescription {

    private Long id;
    private Long patientId;
    private Doctor doctor;
    private LocalDate issuedDate;
    private List<PrescriptionMedication> medications = new ArrayList<>();
    private String instructions;

    public Prescription() {
    }

    public Prescription(Doctor doctor, LocalDate issuedDate) {
        this.doctor = doctor;
        this.issuedDate = issuedDate;
    }

    public Prescription(Long id, Long patientId, Doctor doctor, LocalDate issuedDate, List<PrescriptionMedication> medications, String instructions) {
        this.id = id;
        this.patientId = patientId;
        this.doctor = doctor;
        this.issuedDate = issuedDate;
        this.medications = medications;
        this.instructions = instructions;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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

    public List<PrescriptionMedication> getMedications() {
        return medications;
    }
    public void setMedications(List<PrescriptionMedication> medications) {
        this.medications = medications;
    }

    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Prescription{id=" + id + ", patientId=" + patientId +
                ", doctor=" + doctor + ", issuedDate=" + issuedDate +
                ", medications=" + medications + ", instructions='" + instructions + "'}";
    }

}
