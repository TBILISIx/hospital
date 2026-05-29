package com.solvd.hospital.decorator;

import com.solvd.hospital.model.Patient;
import com.solvd.hospital.service.PatientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 *  adds timing/logging around every PatientService call.
 */
public class LoggingPatientServiceDecorator implements PatientService {

    private static final Logger LOGGER = LogManager.getLogger(LoggingPatientServiceDecorator.class);

    private final PatientService delegate;

    public LoggingPatientServiceDecorator(PatientService delegate) {
        this.delegate = delegate;
    }

    @Override
    public Patient registerPatient(Patient patient) {
        LOGGER.info("[DECORATOR] registerPatient called for {} {}", patient.getFirstName(), patient.getLastName());
        long start = System.currentTimeMillis();
        Patient result = delegate.registerPatient(patient);
        LOGGER.info("[DECORATOR] registerPatient completed in {}ms", System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public void updatePatient(Patient patient) {
        LOGGER.info("[DECORATOR] updatePatient called for id={}", patient.getId());
        delegate.updatePatient(patient);
    }

    @Override
    public void removePatient(Long patientId) {
        LOGGER.info("[DECORATOR] removePatient called for id={}", patientId);
        delegate.removePatient(patientId);
    }

    @Override
    public Patient getPatientById(Long patientId) {
        LOGGER.info("[DECORATOR] getPatientById called for id={}", patientId);
        return delegate.getPatientById(patientId);
    }

    @Override
    public List<Patient> getAllPatients() {
        LOGGER.info("[DECORATOR] getAllPatients called");
        List<Patient> result = delegate.getAllPatients();
        LOGGER.info("[DECORATOR] getAllPatients returned {} records", result.size());
        return result;
    }

}
