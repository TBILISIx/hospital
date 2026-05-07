package com.solvd.hospital.enums;

import java.time.LocalDate;

public class Staff {

    public enum StaffRole {
        NURSE, RECEPTIONIST, TECHNICIAN
    }

    private Integer id;
    private String firstName;
    private String lastName;
    private StaffRole role;
    private Department department;
    private LocalDate hireDate;
    private boolean active;

    public Staff() {
    }

    public Staff(String firstName, String lastName, StaffRole role, Department department, LocalDate hireDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.department = department;
        this.hireDate = hireDate;
        this.active = true;
    }

    public Staff(Integer id, String firstName, String lastName, StaffRole role, Department department, LocalDate hireDate, boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.department = department;
        this.hireDate = hireDate;
        this.active = active;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public StaffRole getRole() { return role; }
    public void setRole(StaffRole role) { this.role = role; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return "Staff{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName +
                "', role=" + role + ", department=" + department +
                ", hireDate=" + hireDate + ", active=" + active + "}";
    }
}
