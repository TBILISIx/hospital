package com.solvd.hospital.service;

import com.solvd.hospital.model.Admission;
import com.solvd.hospital.service.impl.AdmissionServiceImpl;
import com.solvd.hospital.testfakes.FakeAdmissionDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.util.List;

public class AdmissionServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger(AdmissionServiceImplTest.class);

    private FakeAdmissionDao fakeAdmissionDao;
    private AdmissionServiceImpl admissionService;

    // SUITE LEVEL
    //  runs exactly ONCE, before any class in that suite starts (not once per class).
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        LOGGER.info("===== Clinical Operations Suite STARTED =====");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        LOGGER.info("===== Clinical Operations Suite FINISHED =====");
    }

    // CLASS LEVEL 
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
        fakeAdmissionDao = new FakeAdmissionDao();
        admissionService = new AdmissionServiceImpl(fakeAdmissionDao);
        // fake data dependency and admission service declared so i can
        // use its implementation and test methods like admitPatient and so on...
    }

    @AfterMethod
    public void tearDown() {
        LOGGER.info("Finished test method on {}", getClass().getSimpleName());
    }

    @Test(expectedExceptions = IllegalArgumentException.class, description = "patientId and roomId are required / this for patient id")
    // just like in AdmissionServiceImpl in Order
    public void testAdmitPatient_NullPatientId_ThrowsException() {
        admissionService.admitPatient(validAdmission(), null, 2L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, description = "patientId and roomId are required / this room id")
    public void testAdmitPatient_NullRoomId_ThrowsException() {
        admissionService.admitPatient(validAdmission(), 1L, null);
    }

    private Admission validAdmission() {
        Admission admission = new Admission(); // admission clean object  & validAdmission just returns null admission object
        admission.setReason("Routine checkup");
        return admission;
    }

    @Test(description = "If admittedAt is missing, set it automatically to realtime", enabled = true)

    public void testAdmitPatient_IfAdmittedAtNotSet_SetsItToNow() {
        Admission result = admissionService.admitPatient(validAdmission(), 1L, 2L); // not null anymore / needed for test

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(result.getId()); // not writing comment here because if it fails its obvious id was null

        softAssert.assertNotNull(result.getAdmittedAt(), "If admitted is null its admission should default to real time, " +
                "check admissionServiceImpl, if you see this message it does not work");
        // I write message here because if this does not work,
        // service layer business logic fails cause that's where if null it defaults to real time

        softAssert.assertAll(); // “Now check everything I collected. If anything failed, FAIL the test.”
        // softAssert itself just collects results in memory without assertAll it won't throw error

    }

    @Test(description = "If admittedAt is set, it should be kept as it is", enabled = true)
    public void testAdmitPatient_KeepsExplicitAdmittedAt() {
        Admission admission = validAdmission();
        LocalDateTime fixedTime = LocalDateTime.of(2025, 5, 1, 8, 0);
        admission.setAdmittedAt(fixedTime);

        Admission result = admissionService.admitPatient(admission, 1L, 2L);

        Assert.assertEquals(result.getAdmittedAt(), fixedTime);
    }

    @Test
    public void testDischargePatient_SetsDischargedAt() {
        Admission admission = admissionService.admitPatient(validAdmission(), 1L, 2L);

        admissionService.dischargePatient(admission.getId());

        Admission updated = fakeAdmissionDao.findById(admission.getId()).orElseThrow();
        Assert.assertNotNull(updated.getDischargedAt(), "DischargedAt should be set after discharge automatically");
    }

    @Test(expectedExceptions = RuntimeException.class, description = "After Second discharge on same id, exception should be thrown" +
            "because patient is already discharged")
    public void testDischargePatient_AlreadyDischarged_ThrowsException() {
        Admission admission = admissionService.admitPatient(validAdmission(), 1L, 2L);
        admissionService.dischargePatient(admission.getId());

        admissionService.dischargePatient(admission.getId());
    }

    @Test(expectedExceptions = RuntimeException.class, description = "if patient is not found with admission id, should throw exception")
    public void testDischargePatient_NotFound_ThrowsException() {
        admissionService.dischargePatient(999L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, description = "if admission id is null, should throw exception")
    public void testUpdateAdmission_NullId_ThrowsException() {
        admissionService.updateAdmission(validAdmission());
    }

    @Test
    public void testGetAdmissionsForPatient_ReturnsOnlyMatching() {
        admissionService.admitPatient(validAdmission(), 1L, 2L);
        admissionService.admitPatient(validAdmission(), 2L, 3L);

        List<Admission> result = admissionService.getAdmissionsForPatient(1L);

        Assert.assertEquals(result.size(), 1, "Should only return admissions for this specific patient id");
    }

    @Test(description = "Should return only active admissions")
    public void testGetActiveAdmissions_ExcludesDischarged() {
        Admission first = admissionService.admitPatient(validAdmission(), 1L, 2L);
        Admission second = admissionService.admitPatient(validAdmission(), 2L, 3L);
        admissionService.dischargePatient(second.getId());

        List<Admission> active = admissionService.getActiveAdmissions();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(active.size(), 1);
        softAssert.assertEquals(active.get(0).getId(), first.getId());
        softAssert.assertAll();
    }

}
