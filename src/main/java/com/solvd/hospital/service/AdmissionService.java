package com.solvd.hospital.service;

import com.solvd.hospital.model.Admission;

import java.util.List;

public interface AdmissionService {

    Admission admitPatient(Admission admission, Long patientId, Long roomId);

    void dischargePatient(Long admissionId);

    void updateAdmission(Admission admission);

    void deleteAdmission(Long admissionId);

    List<Admission> getAdmissionsForPatient(Long patientId);

    List<Admission> getActiveAdmissions();

}
