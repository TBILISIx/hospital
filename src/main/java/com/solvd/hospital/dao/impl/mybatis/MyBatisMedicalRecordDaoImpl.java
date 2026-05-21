package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.MyBatisSessionHolder;
import com.solvd.hospital.dao.MedicalRecordDao;
import com.solvd.hospital.model.MedicalRecord;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MyBatisMedicalRecordDaoImpl implements MedicalRecordDao {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisMedicalRecordDaoImpl.class);
    private static final String NAMESPACE = "com.solvd.hospital.dao.mybatis.MyBatisMedicalRecordDao.";

    @Override
    public void create(MedicalRecord medicalRecord, Long patientId) {
        Map<String, Object> params = new HashMap<>();
        params.put("medicalRecord", medicalRecord);
        params.put("patientId", patientId);
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.insert(NAMESPACE + "create", params);
            LOGGER.info("Created medical record id={}", medicalRecord.getId());
        }
    }

    @Override
    public void update(MedicalRecord medicalRecord) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.update(NAMESPACE + "update", medicalRecord);
            LOGGER.info("Updated medical record id={}", medicalRecord.getId());
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.delete(NAMESPACE + "delete", id);
            LOGGER.info("Deleted medical record id={}", id);
        }
    }

    @Override
    public Optional<MedicalRecord> findById(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            MedicalRecord record = session.selectOne(NAMESPACE + "findById", id);
            return Optional.ofNullable(record);
        }
    }

    @Override
    public Optional<MedicalRecord> findByPatientId(Long patientId) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            MedicalRecord record = session.selectOne(NAMESPACE + "findByPatientId", patientId);
            return Optional.ofNullable(record);
        }
    }
}