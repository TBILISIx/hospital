package com.solvd.hospital.service;

import com.solvd.hospital.model.Prescription;
import com.solvd.hospital.service.impl.PrescriptionServiceImpl;
import com.solvd.hospital.testfakes.FakePrescriptionDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.List;

public class PrescriptionServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger(PrescriptionServiceImplTest.class);

    private FakePrescriptionDao prescriptionDao;
    private PrescriptionServiceImpl prescriptionService;

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
        prescriptionDao = new FakePrescriptionDao();
        prescriptionService = new PrescriptionServiceImpl(prescriptionDao);
    }

    @AfterMethod
    public void tearDown() {
        LOGGER.info("Finished test method on {}", getClass().getSimpleName());
    }

    private Prescription validPrescription() {
        Prescription prescription = new Prescription();
        prescription.setInstructions("Take with food");
        return prescription;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIssuePrescription_NullDoctorId_ThrowsException() {
        prescriptionService.issuePrescription(validPrescription(), null, 2L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIssuePrescription_NullPatientId_ThrowsException() {
        prescriptionService.issuePrescription(validPrescription(), 1L, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdatePrescription_NullId_ThrowsException() {
        prescriptionService.updatePrescription(validPrescription());
    }

    @Test
    public void testIssuePrescription_SetsIssuedDateWhenMissing() {
        Prescription result = prescriptionService.issuePrescription(validPrescription(), 1L, 2L);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(result.getId());
        softAssert.assertEquals(result.getIssuedDate(), LocalDate.now());
        softAssert.assertEquals(result.getInstructions(), "Take with food");
        softAssert.assertAll();
    }

    @Test
    public void testIssuePrescription_KeepsExplicitIssuedDate() {
        Prescription prescription = validPrescription();
        LocalDate fixedDate = LocalDate.of(2025, 5, 5);
        prescription.setIssuedDate(fixedDate);

        Prescription result = prescriptionService.issuePrescription(prescription, 1L, 2L);

        Assert.assertEquals(result.getIssuedDate(), fixedDate);
    }

    @Test
    public void testDeletePrescription_RemovesFromStore() {
        Prescription prescription = prescriptionService.issuePrescription(validPrescription(), 1L, 2L);

        prescriptionService.deletePrescription(prescription.getId());

        Assert.assertThrows(RuntimeException.class,
                () -> prescriptionService.getPrescriptionById(prescription.getId()));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testGetPrescriptionById_NotFound_ThrowsException() {
        prescriptionService.getPrescriptionById(999L);
    }

    @Test
    public void testGetPrescriptionsForPatient_ReturnsOnlyMatching() {
        prescriptionService.issuePrescription(validPrescription(), 1L, 2L);
        prescriptionService.issuePrescription(validPrescription(), 1L, 3L);

        List<Prescription> result = prescriptionService.getPrescriptionsForPatient(2L);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(result.size(), 1);
        softAssert.assertEquals(result.get(0).getInstructions(), "Take with food");
        softAssert.assertAll();
    }

}
