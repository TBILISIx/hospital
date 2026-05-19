package com.solvd.hospital.model;

import java.time.LocalDate;

public class Staff {

    public enum StaffRole {
        NURSE, RECEPTIONIST, TECHNICIAN
    }

    private Long id;
    private String firstName;
    private String lastName;
    private StaffRole role;
    private LocalDate hireDate;
    private boolean active;
    private Long departmentId;

    public Staff(Long id, String firstName, String lastName, StaffRole role, LocalDate hireDate, boolean active, Long departmentId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.hireDate = hireDate;
        this.active = active;
        this.departmentId = departmentId;
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

    public StaffRole getRole() {
        return role;
    }

    public void setRole(StaffRole role) {
        this.role = role;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", hireDate=" + hireDate +
                ", active=" + active +
                ", departmentId=" + departmentId +
                '}';
    }

}
