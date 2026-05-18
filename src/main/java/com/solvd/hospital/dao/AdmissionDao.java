package com.solvd.hospital.dao;

import com.solvd.hospital.model.Admission;

import java.util.List;
import java.util.Optional;

public interface AdmissionDao {

    void create(Admission admission, Long patientId, Long roomId);

    void update(Admission admission);

    void delete(Long id);

    Optional<Admission> findById(Long id);

    List<Admission> findByPatientId(Long patientId);

    List<Admission> findActive();

}
