package com.solvd.hospital.service.impl;

import com.solvd.hospital.dao.PrescriptionDao;
import com.solvd.hospital.model.Prescription;
import com.solvd.hospital.service.PrescriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

public class PrescriptionServiceImpl implements PrescriptionService {

    private static final Logger LOGGER = LogManager.getLogger(PrescriptionServiceImpl.class);

    private final PrescriptionDao prescriptionDao;

    public PrescriptionServiceImpl(PrescriptionDao prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    @Override
    public Prescription issuePrescription(Prescription prescription, Long doctorId, Long patientId) {

        if (doctorId == null || patientId == null) {
            throw new IllegalArgumentException("doctorId and patientId are required");
        }

        if (prescription.getIssuedDate() == null) {
            prescription.setIssuedDate(LocalDate.now());
        }

        prescriptionDao.create(prescription, doctorId, patientId);

        LOGGER.info(
                "Issued prescription id={} doctor={} patient={}",
                prescription.getId(),
                doctorId,
                patientId
        );

        return prescription;
    }

    @Override
    public void updatePrescription(Prescription prescription) {

        if (prescription.getId() == null) {
            throw new IllegalArgumentException("Cannot update a prescription without an id");
        }

        prescriptionDao.update(prescription);

        LOGGER.info("Updated prescription id={}", prescription.getId());
    }

    @Override
    public void deletePrescription(Long prescriptionId) {

        prescriptionDao.delete(prescriptionId);

        LOGGER.info("Deleted prescription id={}", prescriptionId);
    }

    @Override
    public Prescription getPrescriptionById(Long prescriptionId) {

        return prescriptionDao.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException(
                        "Prescription not found with id=" + prescriptionId
                ));
    }

    @Override
    public List<Prescription> getPrescriptionsForPatient(Long patientId) {
        return prescriptionDao.findByPatientId(patientId);
    }
}