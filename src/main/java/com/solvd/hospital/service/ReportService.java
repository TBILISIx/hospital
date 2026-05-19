package com.solvd.hospital.service;

import com.solvd.hospital.model.Patient;

import java.util.List;

public interface ReportService {

    List<Patient> getPatientAdmissionReport();

}
