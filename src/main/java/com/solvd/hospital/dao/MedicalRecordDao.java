package com.solvd.hospital.dao;

import com.solvd.hospital.model.MedicalRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface MedicalRecordDao {

    void create(@Param("medicalRecord") MedicalRecord medicalRecord,
                @Param("patientId") Long patientId);

    void update(MedicalRecord medicalRecord);

    void delete(Long id);

    Optional<MedicalRecord> findById(Long id);

    Optional<MedicalRecord> findByPatientId(Long patientId);
}