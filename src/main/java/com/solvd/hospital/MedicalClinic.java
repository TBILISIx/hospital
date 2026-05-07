package com.solvd.hospital;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MedicalClinic {

    private String name;
    private String address;
    private Integer totalPatientRooms;
    private Integer occupiedPatientRooms;

    private List<Department> departments;
    private List<Patient> patients;
    private List<Appointment> appointments;
    private List<Admission> admissions;
    private List<Payment> payments;
}
