package com.solvd.hospital;

import java.time.LocalDateTime;

public class Appointment {

    public enum AppointmentStatus {
        SCHEDULED, DONE, CANCELLED
    }

    private Long id;
    private Long patientId;
    private Doctor doctor;
    private LocalDateTime scheduledAt;
    private AppointmentStatus status;
    private String notes;

    public Appointment() {
    }

    public Appointment(Doctor doctor, LocalDateTime scheduledAt) {
        this.doctor = doctor;
        this.scheduledAt = scheduledAt;
        this.status = AppointmentStatus.SCHEDULED;
    }

    public Appointment(Long id, Long patientId, Doctor doctor, LocalDateTime scheduledAt, AppointmentStatus status, String notes) {
        this.id = id;
        this.patientId = patientId;
        this.doctor = doctor;
        this.scheduledAt = scheduledAt;
        this.status = status;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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

    @Override
    public String toString() {
        return "Appointment{id=" + id + ", patientId=" + patientId +
                ", doctor=" + doctor + ", scheduledAt=" + scheduledAt +
                ", status=" + status + ", notes='" + notes + "'}";
    }

}
