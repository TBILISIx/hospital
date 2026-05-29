package com.solvd.hospital.factory;

import com.solvd.hospital.dao.*;
import com.solvd.hospital.dao.impl.jdbc.*;

public class JdbcDaoFactory implements DaoFactory {

    @Override
    public PatientDao createPatientDao() {
        return new JDBCPatientDaoImpl();
    }

    @Override
    public DoctorDao createDoctorDao() {
        return new JDBCDoctorDaoImpl();
    }

    @Override
    public AppointmentDao createAppointmentDao() {
        return new JDBCAppointmentDaoImpl();
    }

    @Override
    public AdmissionDao createAdmissionDao() {
        return new JDBCAdmissionDaoImpl();
    }

    @Override
    public PaymentDao createPaymentDao() {
        return new JDBCPaymentDaoImpl();
    }

    @Override
    public PrescriptionDao createPrescriptionDao() {
        return new JDBCPrescriptionDaoImpl();
    }

    @Override
    public MedicalRecordDao createMedicalRecordDao() {
        return new JDBCMedicalRecordDaoImpl();
    }

    @Override
    public ReportDao createReportDao() {
        return new JDBCReportDaoImpl();
    }

}
