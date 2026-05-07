package com.solvd.hospital;

import java.time.LocalDateTime;

public class Admission {

    private Long id;
    private Patient patient;
    private Room room;
    private LocalDateTime admittedAt;
    private LocalDateTime dischargedAt;
    private String reason;
    private Payment payment;

    public Admission() {
    }

    public Admission(Patient patient, Room room, LocalDateTime admittedAt, String reason) {
        this.patient = patient;
        this.room = room;
        this.admittedAt = admittedAt;
        this.reason = reason;
    }

    public Admission(Long id, Patient patient, Room room, LocalDateTime admittedAt, LocalDateTime dischargedAt, String reason, Payment payment) {
        this.id = id;
        this.patient = patient;
        this.room = room;
        this.admittedAt = admittedAt;
        this.dischargedAt = dischargedAt;
        this.reason = reason;
        this.payment = payment;
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

    public Payment getPayment() {
        return payment;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        // Use patient id only to avoid circular Patient -> Admission -> Patient toString loop
        return "Admission{id=" + id +
                ", patientId=" + (patient != null ? patient.getId() : null) +
                ", room=" + room +
                ", admittedAt=" + admittedAt +
                ", dischargedAt=" + dischargedAt +
                ", reason='" + reason + "'" +
                ", payment=" + payment + "}";
    }

}
