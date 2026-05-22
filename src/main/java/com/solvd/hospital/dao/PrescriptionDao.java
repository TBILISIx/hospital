package com.solvd.hospital.dao;

import com.solvd.hospital.model.Prescription;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface PrescriptionDao {

    void create(@Param("prescription") Prescription prescription,
                @Param("doctorId") Long doctorId,
                @Param("patientId") Long patientId);

    void update(Prescription prescription);

    void delete(Long id);

    Optional<Prescription> findById(Long id);

    List<Prescription> findByPatientId(Long patientId);
}