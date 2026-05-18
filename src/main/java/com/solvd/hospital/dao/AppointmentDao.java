package com.solvd.hospital.dao;

import com.solvd.hospital.model.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentDao {

    void create(Appointment appointment, Long patientId, Long doctorId);

    void update(Appointment appointment);

    void delete(Long id);

    Optional<Appointment> findById(Long id);

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);

}
