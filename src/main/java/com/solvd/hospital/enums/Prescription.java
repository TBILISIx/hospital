package com.solvd.hospital.enums;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prescription {

    private Integer id;
    private Patient patient;
    private Doctor doctor;
    private LocalDate issuedDate;
    private List<PrescriptionMedication> medications = new ArrayList<>();
    private String instructions;

    public Prescription() {
    }

    public Prescription(Patient patient, Doctor doctor, LocalDate issuedDate) {
        this.patient = patient;
        this.doctor = doctor;
        this.issuedDate = issuedDate;
    }

    public Prescription(Integer id, Patient patient, Doctor doctor, LocalDate issuedDate, List<PrescriptionMedication> medications, String instructions) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.issuedDate = issuedDate;
        this.medications = medications;
        this.instructions = instructions;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public LocalDate getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDate issuedDate) { this.issuedDate = issuedDate; }

    public List<PrescriptionMedication> getMedications() { return medications; }
    public void setMedications(List<PrescriptionMedication> medications) { this.medications = medications; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    @Override
    public String toString() {
        return "Prescription{id=" + id + ", patient=" + patient + ", doctor=" + doctor +
                ", issuedDate=" + issuedDate + ", medications=" + medications +
                ", instructions='" + instructions + "'}";
    }
}
