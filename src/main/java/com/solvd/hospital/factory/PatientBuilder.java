package com.solvd.hospital.factory;

import com.solvd.hospital.model.*;

import java.time.LocalDate;
import java.util.List;

public class PatientBuilder {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private boolean insured;
    private MedicalRecord medicalRecord;
    private List<Appointment> appointments;
    private Admission admission;
    private List<Prescription> prescriptions;

    public PatientBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public PatientBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PatientBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PatientBuilder dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public PatientBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public PatientBuilder insured(boolean insured) {
        this.insured = insured;
        return this;
    }

    public PatientBuilder medicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
        return this;
    }

    public PatientBuilder appointments(List<Appointment> appointments) {
        this.appointments = appointments;
        return this;
    }

    public PatientBuilder admission(Admission admission) {
        this.admission = admission;
        return this;
    }

    public PatientBuilder prescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
        return this;
    }

    public Patient build() {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalStateException("Patient first name is required");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalStateException("Patient last name is required");
        }
        return new Patient(id, firstName, lastName, dateOfBirth,
                phoneNumber, insured, medicalRecord, appointments, admission, prescriptions);
    }

}
