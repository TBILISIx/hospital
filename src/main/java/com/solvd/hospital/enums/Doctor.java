package com.solvd.hospital.enums;

public class Doctor {

    private Integer id;
    private String firstName;
    private String lastName;
    private String specialization;
    private boolean available;
    private Department department;

    public Doctor() {
    }

    public Doctor(String firstName, String lastName, String specialization, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.department = department;
    }

    public Doctor(Integer id, String firstName, String lastName, String specialization, boolean available, Department department) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.available = available;
        this.department = department;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    @Override
    public String toString() {
        return "Doctor{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName +
                "', specialization='" + specialization + "', available=" + available +
                ", department=" + department + "}";
    }
}
