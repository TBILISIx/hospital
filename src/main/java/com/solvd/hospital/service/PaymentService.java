package com.solvd.hospital.service;

import com.solvd.hospital.model.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    Payment createPayment(Payment payment);

    void recordPayment(Long paymentId, BigDecimal amountPaid);

    void deletePayment(Long paymentId);

    Payment getPaymentForAdmission(Long admissionId);

    List<Payment> getUnpaidPayments();

}
