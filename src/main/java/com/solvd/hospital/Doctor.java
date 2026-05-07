package com.solvd.hospital;

public class Doctor {

    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
    private boolean available;

    public Doctor() {
    }

    public Doctor(String firstName, String lastName, String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
    }

    public Doctor(Long id, String firstName, String lastName, String specialization, boolean available) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.available = available;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Doctor{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName +
                "', specialization='" + specialization + "', available=" + available + "}";
    }

}
