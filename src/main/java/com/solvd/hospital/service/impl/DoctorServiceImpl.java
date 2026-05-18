package com.solvd.hospital.service.impl;

import com.solvd.hospital.dao.DoctorDao;
import com.solvd.hospital.model.Doctor;
import com.solvd.hospital.service.DoctorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DoctorServiceImpl implements DoctorService {

    private static final Logger LOGGER = LogManager.getLogger(DoctorServiceImpl.class);

    private final DoctorDao doctorDao;

    public DoctorServiceImpl(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }

    @Override
    public Doctor addDoctor(Doctor doctor) {

        if (doctor.getFirstName() == null || doctor.getFirstName().isBlank()) {
            throw new IllegalArgumentException("Doctor first name is required");
        }

        if (doctor.getDepartmentId() == null) {
            throw new IllegalArgumentException("Doctor must be assigned to a department");
        }

        doctorDao.create(doctor);

        LOGGER.info("Added doctor: {} {}", doctor.getFirstName(), doctor.getLastName());

        return doctor;
    }

    @Override
    public void updateDoctor(Doctor doctor) {

        if (doctor.getId() == null) {
            throw new IllegalArgumentException("Cannot update a doctor without an id");
        }

        doctorDao.update(doctor);

        LOGGER.info("Updated doctor id={}", doctor.getId());
    }

    @Override
    public void removeDoctor(Long doctorId) {

        doctorDao.delete(doctorId);

        LOGGER.info("Removed doctor id={}", doctorId);
    }

    @Override
    public Doctor getDoctorById(Long doctorId) {

        return doctorDao.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id=" + doctorId));
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorDao.findAll();
    }

    @Override
    public List<Doctor> getAvailableDoctors() {
        return doctorDao.findByAvailability(true);
    }

    @Override
    public void setAvailability(Long doctorId, boolean available) {

        Doctor doctor = getDoctorById(doctorId);

        doctor.setAvailable(available);

        doctorDao.update(doctor);

        LOGGER.info("Set doctor id={} available={}", doctorId, available);
    }
}