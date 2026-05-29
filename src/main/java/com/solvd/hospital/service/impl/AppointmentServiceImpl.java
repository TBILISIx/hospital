package com.solvd.hospital.service.impl;

import com.solvd.hospital.dao.AppointmentDao;
import com.solvd.hospital.model.Appointment;
import com.solvd.hospital.model.Appointment.AppointmentStatus;
import com.solvd.hospital.service.AppointmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger LOGGER = LogManager.getLogger(AppointmentServiceImpl.class);

    private final AppointmentDao appointmentDao;

    public AppointmentServiceImpl(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    @Override
    public Appointment bookAppointment(Appointment appointment) {

        var doctor    = appointment.getDoctor();
        var patientId = appointment.getPatientID();

        if (!doctor.isAvailable()) {
            throw new RuntimeException("Doctor id=" + doctor.getId() + " is not available");
        }

        if (appointment.getScheduledAt() == null) {
            throw new IllegalArgumentException("Appointment must have a scheduled date/time");
        }

        if (appointment.getStatus() == null) {
            appointment.setStatus(AppointmentStatus.SCHEDULED);
        }

        appointmentDao.create(appointment, patientId, doctor.getId());

        LOGGER.info(
                "Booked appointment id={} patient={} doctor={}",
                appointment.getId(),
                patientId,
                doctor.getId()
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
