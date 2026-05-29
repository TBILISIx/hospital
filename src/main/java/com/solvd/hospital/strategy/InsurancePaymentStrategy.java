package com.solvd.hospital.strategy;

import com.solvd.hospital.model.Payment;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InsurancePaymentStrategy implements PaymentStrategy {

    private static final BigDecimal INSURANCE_COVERAGE = new BigDecimal("0.80");

    @Override
    public BigDecimal calculatePayment(Payment payment, BigDecimal requested) {

        return requested.multiply(INSURANCE_COVERAGE).setScale(2, RoundingMode.HALF_UP);
    }

}
