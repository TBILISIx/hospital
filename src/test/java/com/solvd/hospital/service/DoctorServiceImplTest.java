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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddDoctor_BlankFirstName_ThrowsException() {
        Doctor doctor = validDoctor();
        doctor.setFirstName(" ");

        doctorService.addDoctor(doctor);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddDoctor_NullDepartmentId_ThrowsException() {
        Doctor doctor = validDoctor();
        doctor.setDepartmentId(null);

        doctorService.addDoctor(doctor);
    }

    @Test
    public void testAddDoctor_AssignsIdAndPersists() {
        Doctor result = doctorService.addDoctor(validDoctor());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(result.getId());
        softAssert.assertEquals(result.getSpecialization(), "Cardiologist");
        softAssert.assertTrue(result.isAvailable(), "New doctors should be available by default");
        softAssert.assertAll();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateDoctor_NullId_ThrowsException() {
        Doctor doctor = validDoctor();
        doctor.setId(null);

        doctorService.updateDoctor(doctor);
    }

    @Test
    public void testRemoveDoctor_RemovesFromStore() {
        Doctor doctor = doctorService.addDoctor(validDoctor());

        doctorService.removeDoctor(doctor.getId());

        Assert.assertThrows(RuntimeException.class,
                () -> doctorService.getDoctorById(doctor.getId()));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testGetDoctorById_NotFound_ThrowsException() {
        doctorService.getDoctorById(123L);
    }

    @Test
    public void testGetAvailableDoctors_FiltersOnlyAvailable() {
        Doctor available = doctorService.addDoctor(validDoctor());

        Doctor unavailable = validDoctor();
        unavailable.setAvailable(false);
        doctorService.addDoctor(unavailable);

        List<Doctor> result = doctorService.getAvailableDoctors();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(result.size(), 1, "Only one doctor should be available");
        softAssert.assertEquals(result.get(0).getId(), available.getId());
        softAssert.assertAll();
    }

    @Test
    public void testSetAvailability_UpdatesFlag() {
        Doctor doctor = doctorService.addDoctor(validDoctor());

        doctorService.setAvailability(doctor.getId(), false);

        Doctor updated = doctorService.getDoctorById(doctor.getId());
        Assert.assertFalse(updated.isAvailable());
    }

}
