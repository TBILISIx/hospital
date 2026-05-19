package com.solvd.hospital.service.impl;

import com.solvd.hospital.dao.AppointmentDao;
import com.solvd.hospital.model.Appointment;
import com.solvd.hospital.model.Appointment.AppointmentStatus;
import com.solvd.hospital.model.Doctor;
import com.solvd.hospital.service.AppointmentService;
import com.solvd.hospital.service.DoctorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger LOGGER = LogManager.getLogger(AppointmentServiceImpl.class);

    private final AppointmentDao appointmentDao;
    private final DoctorService doctorService;

    public AppointmentServiceImpl(AppointmentDao appointmentDao, DoctorService doctorService) {
        this.appointmentDao = appointmentDao;
        this.doctorService = doctorService;
    }

    @Override
    public Appointment bookAppointment(Appointment appointment, Long patientId, Long doctorId) {

        Doctor doctor = doctorService.getDoctorById(doctorId);

        if (!doctor.isAvailable()) {
            throw new RuntimeException("Doctor id=" + doctorId + " is not available");
        }

        if (appointment.getScheduledAt() == null) {
            throw new IllegalArgumentException("Appointment must have a scheduled date/time");
        }

        if (appointment.getStatus() == null) {
            appointment.setStatus(AppointmentStatus.SCHEDULED);
        }

        appointmentDao.create(appointment, patientId, doctorId);

        LOGGER.info(
                "Booked appointment id={} patient={} doctor={}",
                appointment.getId(),
                patientId,
                doctorId
        );

        return appointment;
    }

    @Override
    public void updateAppointment(Appointment appointment) {

        if (appointment.getId() == null) {
            throw new IllegalArgumentException("Cannot update an appointment without an id");
        }

        appointmentDao.update(appointment);

        LOGGER.info("Updated appointment id={}", appointment.getId());
    }

    @Override
    public void cancelAppointment(Long appointmentId) {

        Appointment appointment = appointmentDao.findById(appointmentId)
                .orElseThrow(() ->
                        new RuntimeException("Appointment not found with id=" + appointmentId));

        if (appointment.getStatus() == AppointmentStatus.DONE) {
            throw new RuntimeException("Cannot cancel an appointment that is already DONE");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);

        appointmentDao.update(appointment);

        LOGGER.info("Cancelled appointment id={}", appointmentId);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {

        appointmentDao.delete(appointmentId);

        LOGGER.info("Deleted appointment id={}", appointmentId);
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(Long patientId) {
        return appointmentDao.findByPatientId(patientId);
    }

    @Override
    public List<Appointment> getAppointmentsForDoctor(Long doctorId) {
        return appointmentDao.findByDoctorId(doctorId);
    }
}