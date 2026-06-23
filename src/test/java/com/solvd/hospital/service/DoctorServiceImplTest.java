package com.solvd.hospital.service;

import com.solvd.hospital.model.Doctor;
import com.solvd.hospital.service.impl.DoctorServiceImpl;
import com.solvd.hospital.testfakes.FakeDoctorDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class DoctorServiceImplTest {

    private static final Logger LOGGER = LogManager.getLogger(DoctorServiceImplTest.class);

    private FakeDoctorDao doctorDao;
    private DoctorServiceImpl doctorService;

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
        doctorDao = new FakeDoctorDao();
        doctorService = new DoctorServiceImpl(doctorDao);
    }

    @AfterMethod
    public void tearDown() {
        LOGGER.info("Finished test method on {}", getClass().getSimpleName());
    }

    private Doctor validDoctor() {
        Doctor doctor = new Doctor();
        doctor.setFirstName("Levan");
        doctor.setLastName("Beridze");
        doctor.setSpecialization("Cardiologist");
        doctor.setAvailable(true);
        doctor.setDepartmentId(1L);
        return doctor;
    }

    @Test(expectedExceptions = IllegalArgumentException.class, description = "A blank first name must be rejected with IllegalArgumentException")
    public void testAddDoctorBlankFirstNameThrowsException() {
        Doctor doctor = validDoctor();
        doctor.setFirstName(" ");

        doctorService.addDoctor(doctor);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,description = "A null departmentId must be rejected with IllegalArgumentException")
    public void testAddDoctorNullDepartmentIdThrowsException() {
        Doctor doctor = validDoctor();
        doctor.setDepartmentId(null);

        doctorService.addDoctor(doctor);
    }

    @Test
    public void testAddDoctorAssignsIdAndPersists() {
        Doctor result = doctorService.addDoctor(validDoctor());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(result.getId(),"An id must be assigned to the doctor after creation");
        softAssert.assertEquals(result.getSpecialization(), "Cardiologist");
        softAssert.assertTrue(result.isAvailable(), "New doctors should be available by default");
        softAssert.assertAll();
    }

    @Test(expectedExceptions = IllegalArgumentException.class,description = "Updating a doctor without an id must throw IllegalArgumentException")
    public void testUpdateDoctorNullIdThrowsException() {
        Doctor doctor = validDoctor();
        doctor.setId(null);

        doctorService.updateDoctor(doctor);
    }

    @Test
    public void testRemoveDoctorRemovesFromStore() {
        Doctor doctor = doctorService.addDoctor(validDoctor());

        doctorService.removeDoctor(doctor.getId());

        Assert.assertThrows(RuntimeException.class,
                () -> doctorService.getDoctorById(doctor.getId()));
    }

    @Test(expectedExceptions = RuntimeException.class,description = "Looking up a non-existent doctor id must throw RuntimeException")
    public void testGetDoctorByIdNotFoundThrowsException() {
        doctorService.getDoctorById(123L);
    }

    @Test
    public void testGetAvailableDoctorsFiltersOnlyAvailable() {
        Doctor available = doctorService.addDoctor(validDoctor());

        Doctor unavailable = validDoctor();
        unavailable.setAvailable(false);
        doctorService.addDoctor(unavailable);

        List<Doctor> result = doctorService.getAvailableDoctors();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(result.size(), 1, "Only one doctor should be available");
        softAssert.assertEquals(result.get(0).getId(), available.getId(), "The returned doctor must be the one marked as available");
        softAssert.assertAll();
    }

    @Test
    public void testSetAvailabilityUpdatesFlag() {
        Doctor doctor = doctorService.addDoctor(validDoctor());

        doctorService.setAvailability(doctor.getId(), false);

        Doctor updated = doctorService.getDoctorById(doctor.getId());
        Assert.assertFalse(updated.isAvailable());
    }

}
