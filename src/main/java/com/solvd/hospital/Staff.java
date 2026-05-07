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
    private String role;             // "NURSE", "RECEPTIONIST", "TECHNICIAN"
    private Department department;
    private LocalDate hireDate;
    private boolean active;
}