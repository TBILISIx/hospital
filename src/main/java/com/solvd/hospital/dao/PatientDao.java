package com.solvd.hospital.dao;

import com.solvd.hospital.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientDao {

    void create(Patient patient);

    void update(Patient patient);

    void delete(Long id);

    Optional<Patient> findById(Long id);

    List<Patient> findAll();

}
