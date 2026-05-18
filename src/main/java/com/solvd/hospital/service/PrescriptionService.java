package com.solvd.hospital.service;

import com.solvd.hospital.model.Prescription;

import java.util.List;

public interface PrescriptionService {

    Prescription issuePrescription(Prescription prescription, Long doctorId, Long patientId);

    void updatePrescription(Prescription prescription);

    void deletePrescription(Long prescriptionId);

    Prescription getPrescriptionById(Long prescriptionId);

    List<Prescription> getPrescriptionsForPatient(Long patientId);

}
