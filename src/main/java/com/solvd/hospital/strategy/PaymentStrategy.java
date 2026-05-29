package com.solvd.hospital.strategy;

import com.solvd.hospital.model.Payment;

import java.math.BigDecimal;

public interface PaymentStrategy {

    BigDecimal calculatePayment(Payment payment, BigDecimal requested);

}
