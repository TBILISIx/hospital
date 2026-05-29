package com.solvd.hospital.strategy;

import com.solvd.hospital.model.Payment;

import java.math.BigDecimal;

public class InstallmentPaymentStrategy implements PaymentStrategy {

    private final BigDecimal installmentAmount;

    public InstallmentPaymentStrategy(BigDecimal installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    @Override
    public BigDecimal calculatePayment(Payment payment, BigDecimal requested) {
        BigDecimal remaining = payment.getTotalAmount().subtract(payment.getPaidAmount());
        return installmentAmount.min(remaining);
    }

}
