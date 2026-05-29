package com.solvd.hospital.factory;

import com.solvd.hospital.dao.*;
import com.solvd.hospital.dao.impl.mybatis.*;

public class MyBatisDaoFactory implements DaoFactory {

    @Override
    public PatientDao createPatientDao() {
        return new MyBatisPatientDaoImpl();
    }

    @Override
    public DoctorDao createDoctorDao() {
        return new MyBatisDoctorDaoImpl();
    }

    @Override
    public AppointmentDao createAppointmentDao() {
        return new MyBatisAppointmentDaoImpl();
    }

    @Override
    public AdmissionDao createAdmissionDao() {
        return new MyBatisAdmissionDaoImpl();
    }

    @Override
    public PaymentDao createPaymentDao() {
        return new MyBatisPaymentDaoImpl();
    }

    @Override
    public PrescriptionDao createPrescriptionDao() {
        return new MyBatisPrescriptionDaoImpl();
    }

    @Override
    public MedicalRecordDao createMedicalRecordDao() {
        return new MyBatisMedicalRecordDaoImpl();
    }

    @Override
    public ReportDao createReportDao() {
        return new MyBatisReportDaoImpl();
    }

}
