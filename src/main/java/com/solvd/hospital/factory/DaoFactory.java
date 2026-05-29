package com.solvd.hospital.factory;

import com.solvd.hospital.dao.*;

public interface DaoFactory {

    PatientDao createPatientDao();

    DoctorDao createDoctorDao();

    AppointmentDao createAppointmentDao();

    AdmissionDao createAdmissionDao();

    PaymentDao createPaymentDao();

    PrescriptionDao createPrescriptionDao();

    MedicalRecordDao createMedicalRecordDao();

    ReportDao createReportDao();

}
