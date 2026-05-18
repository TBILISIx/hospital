package com.solvd.hospital.service;

import com.solvd.hospital.model.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment bookAppointment(Appointment appointment, Long patientId, Long doctorId);

    void updateAppointment(Appointment appointment);

    void cancelAppointment(Long appointmentId);

    void deleteAppointment(Long appointmentId);

    List<Appointment> getAppointmentsForPatient(Long patientId);

    List<Appointment> getAppointmentsForDoctor(Long doctorId);

}
