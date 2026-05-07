package com.solvd.hospital;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {

    private Long id;
    private Patient patient;
    private Admission admission;
    private LocalDate issuedDate;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private boolean paid;

    public Payment() {
    }

    public Payment(Patient patient, Admission admission, LocalDate issuedDate, BigDecimal totalAmount) {
        this.patient = patient;
        this.admission = admission;
        this.issuedDate = issuedDate;
        this.totalAmount = totalAmount;
        this.paidAmount = BigDecimal.ZERO;
        this.paid = false;
    }

    public Payment(Long id, Patient patient, Admission admission, LocalDate issuedDate, BigDecimal totalAmount, BigDecimal paidAmount, boolean paid) {
        this.id = id;
        this.patient = patient;
        this.admission = admission;
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

    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Admission getAdmission() {
        return admission;
    }
    public void setAdmission(Admission admission) {
        this.admission = admission;
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

}