package com.solvd.hospital.service;

import com.solvd.hospital.dao.impl.PatientAdmissionReport;

import java.util.List;

public interface ReportService {

    List<PatientAdmissionReport> getPatientAdmissionReport();

}
