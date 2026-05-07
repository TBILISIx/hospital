package com.solvd.hospital;

public class PrescriptionMedication {

    private Integer id;
    private String medicationName;
    private String dosage;
    private String frequency;

    public PrescriptionMedication() {
    }

    public PrescriptionMedication(String medicationName, String dosage, String frequency) {
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public PrescriptionMedication(Integer id, String medicationName, String dosage, String frequency) {
        this.id = id;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getMedicationName() { return medicationName; }
    public void setMedicationName(String medicationName) { this.medicationName = medicationName; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }

    @Override
    public String toString() {
        return "PrescriptionMedication{id=" + id + ", medicationName='" + medicationName +
                "', dosage='" + dosage + "', frequency='" + frequency + "'}";
    }
}
