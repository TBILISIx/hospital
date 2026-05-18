package com.solvd.hospital.service.impl;

import com.solvd.hospital.dao.AdmissionDao;
import com.solvd.hospital.model.Admission;
import com.solvd.hospital.service.AdmissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

public class AdmissionServiceImpl implements AdmissionService {

    private static final Logger LOGGER = LogManager.getLogger(AdmissionServiceImpl.class);

    private final AdmissionDao admissionDao;

    public AdmissionServiceImpl(AdmissionDao admissionDao) {
        this.admissionDao = admissionDao;
    }

    @Override
    public Admission admitPatient(Admission admission, Long patientId, Long roomId) {

        if (patientId == null || roomId == null) {
            throw new IllegalArgumentException("patientId and roomId are required");
        }

        if (admission.getAdmittedAt() == null) {
            admission.setAdmittedAt(LocalDateTime.now());
        }

        admissionDao.create(admission, patientId, roomId);

        LOGGER.info("Admitted patient id={} to room id={}", patientId, roomId);

        return admission;
    }

    @Override
    public void dischargePatient(Long admissionId) {

        Admission admission = admissionDao.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found with id=" + admissionId));

        if (admission.getDischargedAt() != null) {
            throw new RuntimeException("Admission id=" + admissionId + " is already discharged");
        }

        admission.setDischargedAt(LocalDateTime.now());

        admissionDao.update(admission);

        LOGGER.info("Discharged admission id={}", admissionId);
    }

    @Override
    public void updateAdmission(Admission admission) {

        if (admission.getId() == null) {
            throw new IllegalArgumentException("Cannot update an admission without an id");
        }

        admissionDao.update(admission);

        LOGGER.info("Updated admission id={}", admission.getId());
    }

    @Override
    public void deleteAdmission(Long admissionId) {

        admissionDao.delete(admissionId);

        LOGGER.info("Deleted admission id={}", admissionId);
    }

    @Override
    public List<Admission> getAdmissionsForPatient(Long patientId) {
        return admissionDao.findByPatientId(patientId);
    }

    @Override
    public List<Admission> getActiveAdmissions() {
        return admissionDao.findActive();
    }
}