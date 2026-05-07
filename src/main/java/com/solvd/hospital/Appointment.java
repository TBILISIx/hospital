package com.solvd.hospital;

import java.time.LocalDateTime;

public class Appointment {

    private Long id;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime scheduledAt;
    private AppointmentStatus status;
    private String notes;

    public Appointment() {
    }

    public Appointment(Patient patient, Doctor doctor, LocalDateTime scheduledAt) {
        this.patient = patient;
        this.doctor = doctor;
        this.scheduledAt = scheduledAt;
        this.status = AppointmentStatus.SCHEDULED;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }
    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public AppointmentStatus getStatus() {
        return status;
    }
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public enum AppointmentStatus {
        SCHEDULED, DONE, CANCELLED
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", scheduledAt=" + scheduledAt +
                ", status=" + status +
                ", notes='" + notes + '\'' +
                '}';
    }

}
