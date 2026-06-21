package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.util.MyBatisSessionHolder;
import com.solvd.hospital.dao.MedicalRecordDao;
import com.solvd.hospital.model.MedicalRecord;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class MyBatisMedicalRecordDaoImpl implements MedicalRecordDao {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisMedicalRecordDaoImpl.class);

    @Override
    public void create(MedicalRecord medicalRecord, Long patientId) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(MedicalRecordDao.class).create(medicalRecord, patientId);
            LOGGER.info("Created medical record id={}", medicalRecord.getId());
        }
    }

    @Override
    public void update(MedicalRecord medicalRecord) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(MedicalRecordDao.class).update(medicalRecord);
            LOGGER.info("Updated medical record id={}", medicalRecord.getId());
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(MedicalRecordDao.class).delete(id);
            LOGGER.info("Deleted medical record id={}", id);
        }
    }

    @Override
    public Optional<MedicalRecord> findById(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(MedicalRecordDao.class).findById(id);
        }
    }

    @Override
    public Optional<MedicalRecord> findByPatientId(Long patientId) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(MedicalRecordDao.class).findByPatientId(patientId);
        }
    }
}