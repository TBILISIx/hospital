package com.solvd.hospital;

public class PrescriptionMedication {

    private Integer id;
    private Prescription prescription;
    private String medicationName;
    private String dosage;
    private String frequency;

    public PrescriptionMedication() {
    }

    public PrescriptionMedication(Prescription prescription, String medicationName, String dosage, String frequency) {
        this.prescription = prescription;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public PrescriptionMedication(Integer id, Prescription prescription, String medicationName, String dosage, String frequency) {
        this.id = id;
        this.prescription = prescription;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Prescription getPrescription() {
        return prescription;
    }
    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }
    public String getMedicationName() {
        return medicationName;
    }
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }
    public String getDosage() {
        return dosage;
    }
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    public String getFrequency() {
        return frequency;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

}
