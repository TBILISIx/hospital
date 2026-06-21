package com.solvd.hospital.service;

import com.solvd.hospital.model.Patient;
import com.solvd.hospital.service.impl.PatientServiceImpl;
import com.solvd.hospital.testfakes.FakePatientDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.util.List;

public class PatientServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger(PatientServiceImplTest.class);

    private FakePatientDao patientDao;
    private PatientServiceImpl patientService;

    //  SUITE LEVEL
    // runs exactly ONCE, before any class in that suite starts.

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        LOGGER.info("===== People Management Suite STARTED =====");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        LOGGER.info("===== People Management Suite FINISHED =====");
    }

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
        patientDao = new FakePatientDao();
        patientService = new PatientServiceImpl(patientDao);
    }

    @AfterMethod
    public void tearDown() {
        LOGGER.info("Finished test method. DAO create() was called {} time(s)", patientDao.getCreateCallCount());
    }

    private Patient validPatient() {
        Patient patient = new Patient();
        patient.setFirstName("Nino");
        patient.setLastName("Gelashvili");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patient.setPhoneNumber("+995 599 11 22 33");
        patient.setInsured(true);
        return patient;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRegisterPatient_BlankFirstName_ThrowsException() {
        Patient patient = validPatient();
        patient.setFirstName("  ");

        patientService.registerPatient(patient);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRegisterPatient_BlankLastName_ThrowsException() {
        Patient patient = validPatient();
        patient.setLastName("");

        patientService.registerPatient(patient);
    }

    @Test
    public void testRegisterPatient_AssignsIdAndPersists() {
        Patient result = patientService.registerPatient(validPatient());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(result.getId(), "Id should be assigned after registration");
        softAssert.assertEquals(result.getFirstName(), "Nino");
        softAssert.assertEquals(result.getLastName(), "Gelashvili");
        softAssert.assertTrue(result.isInsured());
        softAssert.assertEquals(patientDao.getCreateCallCount(), 1, "DAO create should be called exactly once");
        softAssert.assertAll();
    }

    @Test
    public void testRegisterPatient_InvalidInput_DoesNotCallDao() {
        Patient patient = validPatient();
        patient.setFirstName(null);

        try {
            patientService.registerPatient(patient);
            Assert.fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(patientDao.getCreateCallCount(), 0,
                    "DAO create should never be called when validation fails");
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdatePatient_NullId_ThrowsException() {
        Patient patient = validPatient();
        patient.setId(null);

        patientService.updatePatient(patient);
    }

    @Test
    public void testUpdatePatient_Success_ChangesPersist() {
        Patient registered = patientService.registerPatient(validPatient());
        registered.setPhoneNumber("+995 555 99 88 77");

        patientService.updatePatient(registered);

        Patient fetched = patientService.getPatientById(registered.getId());
        Assert.assertEquals(fetched.getPhoneNumber(), "+995 555 99 88 77");
    }

    @Test
    public void testRemovePatient_RemovesFromStore() {
        Patient registered = patientService.registerPatient(validPatient());

        patientService.removePatient(registered.getId());

        Assert.assertThrows(RuntimeException.class,
                () -> patientService.getPatientById(registered.getId()));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testGetPatientById_NotFound_ThrowsException() {
        patientService.getPatientById(999L);
    }

    @Test
    public void testGetAllPatients_ReturnsAllRegisteredPatients() {
        Patient first = patientService.registerPatient(validPatient());

        Patient second = validPatient();
        second.setFirstName("Tamar");
        second.setLastName("Kvaratskhelia");
        patientService.registerPatient(second);

        List<Patient> all = patientService.getAllPatients();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(all.size(), 2, "Both registered patients should be returned");
        softAssert.assertTrue(all.stream().anyMatch(p -> p.getId().equals(first.getId())), "First patient should be returned");
        softAssert.assertTrue(all.stream().anyMatch(p -> p.getFirstName().equals("Tamar")), "Second patient should be returned");
        softAssert.assertAll();
    }

}
