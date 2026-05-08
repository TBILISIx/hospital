package com.solvd.hospital;

import java.time.LocalDateTime;

public class Admission {

    private Long id;
    private LocalDateTime admittedAt;
    private LocalDateTime dischargedAt;
    private String reason;
    private Payment payment;

    public Admission(Long id, LocalDateTime admittedAt, LocalDateTime dischargedAt, String reason, Payment payment) {
        this.id = id;
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
        return "Admission{" +
                "id=" + id +
                ", admittedAt=" + admittedAt +
                ", dischargedAt=" + dischargedAt +
                ", reason='" + reason + '\'' +
                ", payment=" + payment +
                '}';
    }

}
