package com.solvd.hospital.dao;

import com.solvd.hospital.dao.impl.PatientAdmissionReport;

import java.util.List;

public interface ReportDao {

    List<PatientAdmissionReport> findPatientAdmissionReport();

}
