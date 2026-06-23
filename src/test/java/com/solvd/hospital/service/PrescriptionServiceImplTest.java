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

    @Test(expectedExceptions = IllegalArgumentException.class, description = "A null doctorId must be rejected with IllegalArgumentException")
    public void testIssuePrescriptionNullDoctorIdThrowsException() {
        prescriptionService.issuePrescription(validPrescription(), null, 2L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, description = "A null patientId must be rejected with IllegalArgumentException")
    public void testIssuePrescriptionNullPatientIdThrowsException() {
        prescriptionService.issuePrescription(validPrescription(), 1L, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, description = "Updating a prescription without an id must throw IllegalArgumentException")
    public void testUpdatePrescriptionNullIdThrowsException() {
        prescriptionService.updatePrescription(validPrescription());
    }

    @Test
    public void testIssuePrescriptionSetsIssuedDateWhenMissing() {
        Prescription result = prescriptionService.issuePrescription(validPrescription(), 1L, 2L);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(result.getId(), "An id must be assigned after the prescription is issued");
        softAssert.assertEquals(result.getIssuedDate(), LocalDate.now(), "issuedDate must default to today when not explicitly provided");
        softAssert.assertEquals(result.getInstructions(), "Take with food", "Instructions must be preserved after issuance");
        softAssert.assertAll();
    }

    @Test
    public void testIssuePrescriptionKeepsExplicitIssuedDate() {
        Prescription prescription = validPrescription();
        LocalDate fixedDate = LocalDate.of(2025, 5, 5);
        prescription.setIssuedDate(fixedDate);

        Prescription result = prescriptionService.issuePrescription(prescription, 1L, 2L);

        Assert.assertEquals(result.getIssuedDate(), fixedDate, "An explicitly provided issuedDate must not be overwritten by the service");
    }

    @Test
    public void testDeletePrescriptionRemovesFromStore() {
        Prescription prescription = prescriptionService.issuePrescription(validPrescription(), 1L, 2L);

        prescriptionService.deletePrescription(prescription.getId());

        Assert.assertThrows(RuntimeException.class, () -> prescriptionService.getPrescriptionById(prescription.getId()));
    }

    @Test(expectedExceptions = RuntimeException.class, description = "Looking up a non-existent prescription id must throw RuntimeException")
    public void testGetPrescriptionByIdNotFoundThrowsException() {
        prescriptionService.getPrescriptionById(999L);
    }

    @Test
    public void testGetPrescriptionsForPatientReturnsOnlyMatching() {
        prescriptionService.issuePrescription(validPrescription(), 1L, 2L);
        prescriptionService.issuePrescription(validPrescription(), 1L, 3L);

        List<Prescription> result = prescriptionService.getPrescriptionsForPatient(2L);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(result.size(), 1, "Only prescriptions belonging to patient id=2 should be returned");
        softAssert.assertEquals(result.get(0).getInstructions(), "Take with food", "The returned prescription must carry the correct instructions");
        softAssert.assertAll();
    }

}