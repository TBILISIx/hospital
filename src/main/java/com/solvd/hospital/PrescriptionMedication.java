package com.solvd.hospital;

public class PrescriptionMedication {

    private Integer id;
    private Prescription prescription;
    private String name;
    private String dosage;
    private String frequency;

    public PrescriptionMedication() {
    }

    public PrescriptionMedication(Prescription prescription, String name, String dosage, String frequency) {
        this.prescription = prescription;
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public PrescriptionMedication(Integer id, Prescription prescription, String name, String dosage, String frequency) {
        this.id = id;
        this.prescription = prescription;
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Prescription getPrescription() { return prescription; }
    public void setPrescription(Prescription prescription) { this.prescription = prescription; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }

    @Override
    public String toString() {
        return "PrescriptionMedication{id=" + id + ", name='" + name +
                "', dosage='" + dosage + "', frequency='" + frequency + "'}";
    }
}
