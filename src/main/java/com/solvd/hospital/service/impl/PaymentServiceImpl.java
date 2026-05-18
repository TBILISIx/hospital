package com.solvd.hospital.service.impl;

import com.solvd.hospital.dao.PaymentDao;
import com.solvd.hospital.model.Payment;
import com.solvd.hospital.service.PaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);

    private final PaymentDao paymentDao;

    public PaymentServiceImpl(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Override
    public Payment createPayment(Payment payment) {

        if (payment.getAdmissionId() == null) {
            throw new IllegalArgumentException("Payment must be linked to an admission");
        }

        if (payment.getTotalAmount() == null
                || payment.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total amount must be greater than zero");
        }

        paymentDao.create(payment);

        LOGGER.info(
                "Created payment id={} for admission id={}",
                payment.getId(),
                payment.getAdmissionId()
        );

        return payment;
    }

    @Override
    public void recordPayment(Long paymentId, BigDecimal amountPaid) {

        Payment payment = paymentDao.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with id=" + paymentId));

        if (amountPaid == null || amountPaid.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount paid must be greater than zero");
        }

        BigDecimal newPaid = payment.getPaidAmount().add(amountPaid);

        // Cap at total – can't overpay
        if (newPaid.compareTo(payment.getTotalAmount()) > 0) {
            newPaid = payment.getTotalAmount();
        }

        payment.setPaidAmount(newPaid);

        if (newPaid.compareTo(payment.getTotalAmount()) == 0) {
            payment.setPaid(true);
            LOGGER.info("Payment id={} is now fully paid", paymentId);
        }

        paymentDao.update(payment);
    }

    @Override
    public void deletePayment(Long paymentId) {

        paymentDao.delete(paymentId);

        LOGGER.info("Deleted payment id={}", paymentId);
    }

    @Override
    public Payment getPaymentForAdmission(Long admissionId) {

        return paymentDao.findByAdmissionId(admissionId)
                .orElseThrow(() -> new RuntimeException(
                        "No payment found for admission id=" + admissionId
                ));
    }

    @Override
    public List<Payment> getUnpaidPayments() {
        return paymentDao.findUnpaid();
    }
}