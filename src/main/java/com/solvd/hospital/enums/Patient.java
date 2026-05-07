package com.solvd.hospital.enums;

import java.time.LocalDate;

public class Patient {

    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private boolean insured;

    public Patient() {
    }

    public Patient(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, boolean insured) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.insured = insured;
    }

    public Patient(Integer id, String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber, boolean insured) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.insured = insured;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public boolean isInsured() { return insured; }
    public void setInsured(boolean insured) { this.insured = insured; }

    @Override
    public String toString() {
        return "Patient{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName +
                "', dateOfBirth=" + dateOfBirth + ", phoneNumber='" + phoneNumber +
                "', insured=" + insured + "}";
    }
}
