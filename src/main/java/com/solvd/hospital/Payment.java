package com.solvd.hospital;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long id;
    private Patient patient;
    private Admission admission;
    private LocalDate issuedDate;
    private double totalAmount;
    private double paidAmount;
    private boolean paid;
}
