package com.solvd.hospital;

import java.time.LocalDateTime;

public class Admission {

    private Long id;
    private Patient patient;
    private Room room;
    private LocalDateTime admittedAt;
    private LocalDateTime dischargedAt;
    private String reason;

    public Admission() {
    }

    public Admission(Patient patient, Room room, LocalDateTime admittedAt, String reason, LocalDateTime dischargedAt) {
        this.patient = patient;
        this.room = room;
        this.admittedAt = admittedAt;
        this.reason = reason;
        this.dischargedAt = dischargedAt;
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

    public Room getRoom() {
        return room;
    }
    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getAdmittedAt() {
        return admittedAt;
    }
    public void setAdmittedAt(LocalDateTime admittedAt) {
        this.admittedAt = admittedAt;
    }

    public LocalDateTime getDischargedAt() {
        return dischargedAt;
    }
    public void setDischargedAt(LocalDateTime dischargedAt) {
        this.dischargedAt = dischargedAt;
    }

    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

}