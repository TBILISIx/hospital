package com.solvd.hospital;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private boolean insured;
    private List<Appointment> appointments = new ArrayList<>();
    private MedicalRecord medicalRecord;
    private List<Prescription> prescriptions = new ArrayList<>();
}