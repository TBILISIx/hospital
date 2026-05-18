package com.solvd.hospital.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {

    private Long id;
    private Long admissionId;
    private LocalDate issuedDate;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private boolean paid;

    public Payment() {
    }

    public Payment(LocalDate issuedDate, BigDecimal totalAmount) {
        this.issuedDate = issuedDate;
        this.totalAmount = totalAmount;
        this.paidAmount = BigDecimal.ZERO;
        this.paid = false;
    }

    public Payment(Long id, Long admissionId, LocalDate issuedDate, BigDecimal totalAmount, BigDecimal paidAmount, boolean paid) {
        this.id = id;
        this.admissionId = admissionId;
        this.issuedDate = issuedDate;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.paid = paid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(Long admissionId) {
        this.admissionId = admissionId;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Payment{id=" + id + ", admissionId=" + admissionId +
                ", issuedDate=" + issuedDate + ", totalAmount=" + totalAmount +
                ", paidAmount=" + paidAmount + ", paid=" + paid + "}";
    }

}
