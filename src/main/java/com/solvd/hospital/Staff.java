package com.solvd.hospital;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staff {
    private Long id;
    private String firstName;
    private String lastName;
    private StaffRole role;
    private Department department;
    private LocalDate hireDate;
    private boolean active;

    public enum StaffRole {
        NURSE, RECEPTIONIST, TECHNICIAN, ADMIN, PORTER
    }
}
