package com.solvd.hospital.service;

import com.solvd.hospital.model.Payment;
import com.solvd.hospital.service.impl.PaymentServiceImpl;
import com.solvd.hospital.testfakes.FakePaymentDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PaymentServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImplTest.class);

    private FakePaymentDao paymentDao;
    private PaymentServiceImpl paymentService;

    @BeforeClass
    public void beforeClass() {
        LOGGER.info("--- Starting test class: {} ---", getClass().getSimpleName());
    }

    @AfterClass
    public void afterClass() {
        LOGGER.info("--- Finished test class: {} ---", getClass().getSimpleName());
    }

    @BeforeMethod
    public void setUp() {
        paymentDao = new FakePaymentDao();
        paymentService = new PaymentServiceImpl(paymentDao);
    }

    @AfterMethod
    public void tearDown() {
        LOGGER.info("Finished test method on {}", getClass().getSimpleName());
    }

    private Payment validPayment() {
        Payment payment = new Payment(LocalDate.now(), new BigDecimal("1000.00"));
        payment.setAdmissionId(1L);
        return payment;
    }

    @Test(expectedExceptions = IllegalArgumentException.class, description = "A payment without an admissionId must be rejected")
    public void testCreatePaymentNoAdmissionIdThrowsException() {
        Payment payment = validPayment();
        payment.setAdmissionId(null);

        paymentService.createPayment(payment);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, description = "A payment with a zero total amount must be rejected")
    public void testCreatePaymentZeroAmountThrowsException() {
        Payment payment = validPayment();
        payment.setTotalAmount(BigDecimal.ZERO);

        paymentService.createPayment(payment);
    }

    @Test
    public void testCreatePaymentAssignsIdAndPersists() {
        Payment result = paymentService.createPayment(validPayment());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(result.getId());
        softAssert.assertEquals(result.getTotalAmount(), new BigDecimal("1000.00"), "Payment amount should be set correctly");
        softAssert.assertFalse(result.isPaid(), "Payment should not be marked as paid by default");
        softAssert.assertAll();
    }

    @Test
    public void testRecordPaymentPartialPaymentUpdatesPaidAmountAndStaysUnpaid() {
        Payment payment = paymentService.createPayment(validPayment());

        paymentService.recordPayment(payment.getId(), new BigDecimal("400.00"));

        Payment updated = paymentService.getPaymentForAdmission(1L);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updated.getPaidAmount(), new BigDecimal("400.00"), "paidAmount must reflect the partial payment that was recorded");
        softAssert.assertFalse(updated.isPaid(), "Payment must not be marked as fully paid after a partial payment");
        softAssert.assertAll();
    }

    @Test
    public void testRecordPaymentFullPaymentMarksPaidTrue() {
        Payment payment = paymentService.createPayment(validPayment());

        paymentService.recordPayment(payment.getId(), new BigDecimal("1000.00"));

        Payment updated = paymentService.getPaymentForAdmission(1L);
        Assert.assertTrue(updated.isPaid(), "Payment must be marked as fully paid when the total amount is recorded");
    }

    @Test
    public void testRecordPaymentOverpaymentCapsAtTotalAmount() {
        Payment payment = paymentService.createPayment(validPayment());

        paymentService.recordPayment(payment.getId(), new BigDecimal("5000.00"));

        Payment updated = paymentService.getPaymentForAdmission(1L);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updated.getPaidAmount(), new BigDecimal("1000.00"), "paidAmount must be capped at totalAmount — overpayment is not allowed");
        softAssert.assertTrue(updated.isPaid(), "Payment must be marked as fully paid when paidAmount reaches the cap");
        softAssert.assertAll();
    }

    @Test(expectedExceptions = IllegalArgumentException.class, description = "Recording a negative payment amount must throw IllegalArgumentException")
    public void testRecordPaymentNegativeAmountThrowsException() {
        Payment payment = paymentService.createPayment(validPayment());

        paymentService.recordPayment(payment.getId(), new BigDecimal("-50.00"));
    }

    @Test(expectedExceptions = RuntimeException.class, description = "Looking up a payment for a non-existent admissionId must throw RuntimeException")
    public void testGetPaymentForAdmissionNotFoundThrowsException() {
        paymentService.getPaymentForAdmission(999L);
    }

    @Test
    public void testGetUnpaidPaymentsReturnsOnlyUnpaid() {
        Payment unpaid = paymentService.createPayment(validPayment());

        Payment second = validPayment();
        second.setAdmissionId(2L);
        Payment paid = paymentService.createPayment(second);
        paymentService.recordPayment(paid.getId(), new BigDecimal("1000.00"));

        List<Payment> unpaidList = paymentService.getUnpaidPayments();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(unpaidList.size(), 1, "Only the one unpaid payment should be returned");
        softAssert.assertEquals(unpaidList.get(0).getId(), unpaid.getId(), "The returned payment must be the one that was never fully paid");
        softAssert.assertAll();
    }

}