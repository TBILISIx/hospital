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
public class MedicalRecord {

    private Long id;
    private Patient patient;
    private LocalDate createdDate;
    private String bloodType;
    private List<String> allergies = new ArrayList<>();
    private String notes;

}