package com.solvd.hospital.service.impl;

import com.solvd.hospital.dao.PatientDao;
import com.solvd.hospital.model.Patient;
import com.solvd.hospital.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PatientServiceImpl implements PatientService {

    private static final Logger LOGGER = LogManager.getLogger(PatientServiceImpl.class);

    private final PatientDao patientDao;

    public PatientServiceImpl(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Override
    public Patient registerPatient(Patient patient) {

        if (patient.getFirstName() == null || patient.getFirstName().isBlank()) {
            throw new IllegalArgumentException("Patient first name is required");
        }

        if (patient.getLastName() == null || patient.getLastName().isBlank()) {
            throw new IllegalArgumentException("Patient last name is required");
        }

        patientDao.create(patient);

        LOGGER.info("Registered patient: {} {}", patient.getFirstName(), patient.getLastName());

        return patient;
    }

    @Override
    public void updatePatient(Patient patient) {

        if (patient.getId() == null) {
            throw new IllegalArgumentException("Cannot update a patient without an id");
        }

        patientDao.update(patient);

        LOGGER.info("Updated patient id={}", patient.getId());
    }

    @Override
    public void removePatient(Long patientId) {

        patientDao.delete(patientId);

        LOGGER.info("Removed patient id={}", patientId);
    }

    @Override
    public Patient getPatientById(Long patientId) {

        return patientDao.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id=" + patientId));
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientDao.findAll();
    }
}