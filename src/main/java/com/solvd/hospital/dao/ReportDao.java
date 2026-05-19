package com.solvd.hospital.dao;

import com.solvd.hospital.model.Patient;

import java.util.List;

public interface ReportDao {

    List<Patient> findPatientsWithAdmissionDetails();

}