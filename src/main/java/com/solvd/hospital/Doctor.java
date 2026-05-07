package com.solvd.hospital;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;   // "Cardiology", "Surgery", etc. later for enums if needed
    private boolean available;
    private List<Appointment> appointments = new ArrayList<>();
}