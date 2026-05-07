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
public class Prescription {
    private Long id;
    private Patient patient;
    private Doctor doctor;
    private LocalDate issuedDate;
    private List<String> medications = new ArrayList<>();
    private String instructions;
}