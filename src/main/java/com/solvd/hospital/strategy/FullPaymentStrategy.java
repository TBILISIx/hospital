package com.solvd.hospital.strategy;

import com.solvd.hospital.model.Payment;

import java.math.BigDecimal;

public class FullPaymentStrategy implements PaymentStrategy {

    @Override
    public BigDecimal calculatePayment(Payment payment, BigDecimal requested) {

        return payment.getTotalAmount().subtract(payment.getPaidAmount());
    }

}
