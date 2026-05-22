package com.solvd.hospital.dao;

import com.solvd.hospital.model.Admission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface AdmissionDao {

    void create(@Param("admission") Admission admission,
                @Param("patientId") Long patientId,
                @Param("roomId") Long roomId);

    void update(Admission admission);

    void delete(Long id);

    Optional<Admission> findById(Long id);

    List<Admission> findByPatientId(Long patientId);

    List<Admission> findActive();
}
