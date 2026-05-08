package com.solvd.hospital;

import java.time.LocalDate;
import java.util.List;

public class Prescription {

    private Long id;
    private LocalDate issuedDate;
    private List<PrescriptionMedication> medications;
    private String instructions;

    public Prescription(Long id, LocalDate issuedDate, List<PrescriptionMedication> medications, String instructions) {
        this.id = id;
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
        return "Prescription{" +
                "id=" + id +
                ", issuedDate=" + issuedDate +
                ", medications=" + medications +
                ", instructions='" + instructions + '\'' +
                '}';
    }

}
