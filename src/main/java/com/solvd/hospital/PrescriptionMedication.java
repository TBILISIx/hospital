package com.solvd.hospital;

public class PrescriptionMedication {

    private Long id;
    private String name;
    private String dosage;
    private String frequency;

    public PrescriptionMedication() {
    }

    public PrescriptionMedication(String name, String dosage, String frequency) {
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public PrescriptionMedication(Long id, String name, String dosage, String frequency) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "PrescriptionMedication{id=" + id + ", name='" + name +
                "', dosage='" + dosage + "', frequency='" + frequency + "'}";
    }

}
