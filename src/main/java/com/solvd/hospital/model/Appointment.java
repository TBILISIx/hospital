package com.solvd.hospital.model;

import java.time.LocalDateTime;

public class Appointment {

    public enum AppointmentStatus {
        SCHEDULED, DONE, CANCELLED
    }

    private Long id;
    private Doctor doctor;
    private Long patientID;
    private LocalDateTime scheduledAt;
    private AppointmentStatus status;
    private String notes;

    public Appointment() {

    }


    public Appointment(Long id, Long patientId, Doctor doctor, LocalDateTime scheduledAt, AppointmentStatus status, String notes) {
        this.id = id;
        this.doctor = doctor;
        this.patientID = patientId;
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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
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
        return "Appointment{" +
                "id=" + id +
                ", doctor=" + doctor +
                ", patientID=" + patientID +
                ", scheduledAt=" + scheduledAt +
                ", status=" + status +
                ", notes='" + notes + '\'' +
                '}';
    }

}
