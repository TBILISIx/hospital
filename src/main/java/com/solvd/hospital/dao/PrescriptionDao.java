package com.solvd.hospital.dao;

import com.solvd.hospital.model.Prescription;

import java.util.List;
import java.util.Optional;

public interface PrescriptionDao {

    void create(Prescription prescription, Long doctorId, Long patientId);

    void update(Prescription prescription);

    void delete(Long id);

    Optional<Prescription> findById(Long id);

    List<Prescription> findByPatientId(Long patientId);

}
