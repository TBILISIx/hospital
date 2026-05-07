package com.solvd.hospital.enums;

import java.time.LocalDateTime;

public class Appointment {

    public enum AppointmentStatus {
        SCHEDULED, DONE, CANCELLED
    }

    private Integer id;
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

    public Appointment(Integer id, Patient patient, Doctor doctor, LocalDateTime scheduledAt, AppointmentStatus status, String notes) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.scheduledAt = scheduledAt;
        this.status = status;
        this.notes = notes;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }

    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return "Appointment{id=" + id + ", patient=" + patient + ", doctor=" + doctor +
                ", scheduledAt=" + scheduledAt + ", status=" + status + ", notes='" + notes + "'}";
    }
}
