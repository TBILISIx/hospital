package com.solvd.hospital.service;

import com.solvd.hospital.model.Patient;

import java.util.List;

public interface PatientService {

    Patient registerPatient(Patient patient);

    void updatePatient(Patient patient);

    void removePatient(Long patientId);

    Patient getPatientById(Long patientId);

    List<Patient> getAllPatients();

}
