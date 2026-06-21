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

    //  CLASS LEVEL 
    @BeforeClass
    public void beforeClass() {
        LOGGER.info("--- Starting test class: {} ---", getClass().getSimpleName());
    }

    @AfterClass
    public void afterClass() {
        LOGGER.info("--- Finished test class: {} ---", getClass().getSimpleName());
    }

    //  METHOD LEVEL 
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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreatePayment_NoAdmissionId_ThrowsException() {
        Payment payment = validPayment();
        payment.setAdmissionId(null);

        paymentService.createPayment(payment);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreatePayment_ZeroAmount_ThrowsException() {
        Payment payment = validPayment();
        payment.setTotalAmount(BigDecimal.ZERO);

        paymentService.createPayment(payment);
    }

    @Test
    public void testCreatePayment_AssignsIdAndPersists() {
        Payment result = paymentService.createPayment(validPayment());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(result.getId());
        softAssert.assertEquals(result.getTotalAmount(), new BigDecimal("1000.00"), "Payment amount should be set correctly");
        softAssert.assertFalse(result.isPaid(), "Payment should not be marked as paid by default");
        softAssert.assertAll();
    }

    @Test
    public void testRecordPayment_PartialPayment_UpdatesPaidAmountAndStaysUnpaid() {
        Payment payment = paymentService.createPayment(validPayment());

        paymentService.recordPayment(payment.getId(), new BigDecimal("400.00"));

        Payment updated = paymentService.getPaymentForAdmission(1L);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updated.getPaidAmount(), new BigDecimal("400.00"));
        softAssert.assertFalse(updated.isPaid());
        softAssert.assertAll();
    }

    @Test
    public void testRecordPayment_FullPayment_MarksPaidTrue() {
        Payment payment = paymentService.createPayment(validPayment());

        paymentService.recordPayment(payment.getId(), new BigDecimal("1000.00"));

        Payment updated = paymentService.getPaymentForAdmission(1L);

        Assert.assertTrue(updated.isPaid());
    }

    @Test
    public void testRecordPayment_Overpayment_CapsAtTotalAmount() {
        Payment payment = paymentService.createPayment(validPayment());

        paymentService.recordPayment(payment.getId(), new BigDecimal("5000.00"));

        Payment updated = paymentService.getPaymentForAdmission(1L);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updated.getPaidAmount(), new BigDecimal("1000.00"));
        softAssert.assertTrue(updated.isPaid());
        softAssert.assertAll();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRecordPayment_NegativeAmount_ThrowsException() {
        Payment payment = paymentService.createPayment(validPayment());

        paymentService.recordPayment(payment.getId(), new BigDecimal("-50.00"));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testGetPaymentForAdmission_NotFound_ThrowsException() {
        paymentService.getPaymentForAdmission(999L);
    }

    @Test
    public void testGetUnpaidPayments_ReturnsOnlyUnpaid() {
        Payment unpaid = paymentService.createPayment(validPayment());

        Payment second = validPayment();
        second.setAdmissionId(2L);
        Payment paid = paymentService.createPayment(second);
        paymentService.recordPayment(paid.getId(), new BigDecimal("1000.00"));

        List<Payment> unpaidList = paymentService.getUnpaidPayments();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(unpaidList.size(), 1);
        softAssert.assertEquals(unpaidList.get(0).getId(), unpaid.getId());
        softAssert.assertAll();
    }

}
