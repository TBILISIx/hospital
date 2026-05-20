package com.solvd.hospital.model;

import java.time.LocalDateTime;

public class Appointment {

    public enum AppointmentStatus {
        SCHEDULED, DONE, CANCELLED
    }

    private Long id;
    private LocalDateTime scheduledAt;
    private AppointmentStatus status;
    private String notes;


    public Appointment(Long id,  LocalDateTime scheduledAt, AppointmentStatus status, String notes) {
        this.id = id;
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
                ", scheduledAt=" + scheduledAt +
                ", status=" + status +
                ", notes='" + notes + '\'' +
                '}';
    }

}
