package com.solvd.hospital;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {

    private Integer id;
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

    public Payment(Integer id, LocalDate issuedDate, BigDecimal totalAmount, BigDecimal paidAmount, boolean paid) {
        this.id = id;
        this.issuedDate = issuedDate;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.paid = paid;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDate issuedDate) { this.issuedDate = issuedDate; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public BigDecimal getPaidAmount() { return paidAmount; }
    public void setPaidAmount(BigDecimal paidAmount) { this.paidAmount = paidAmount; }

    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }

    @Override
    public String toString() {
        return "Payment{id=" + id + ", issuedDate=" + issuedDate +
                ", totalAmount=" + totalAmount + ", paidAmount=" + paidAmount + ", paid=" + paid + "}";
    }
}
