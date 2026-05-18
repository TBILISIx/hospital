package com.solvd.hospital.service;

import com.solvd.hospital.model.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor addDoctor(Doctor doctor);

    void updateDoctor(Doctor doctor);

    void removeDoctor(Long doctorId);

    Doctor getDoctorById(Long doctorId);

    List<Doctor> getAllDoctors();

    List<Doctor> getAvailableDoctors();

    void setAvailability(Long doctorId, boolean available);

}
