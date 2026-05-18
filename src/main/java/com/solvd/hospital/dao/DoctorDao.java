package com.solvd.hospital.dao;

import com.solvd.hospital.model.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorDao {

    void create(Doctor doctor);

    void update(Doctor doctor);

    void delete(Long id);

    Optional<Doctor> findById(Long id);

    List<Doctor> findAll();

    List<Doctor> findByAvailability(boolean available);

}
