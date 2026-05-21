package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.MyBatisSessionHolder;
import com.solvd.hospital.dao.PrescriptionDao;
import com.solvd.hospital.model.Prescription;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MyBatisPrescriptionDaoImpl implements PrescriptionDao {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisPrescriptionDaoImpl.class);
    private static final String NAMESPACE = "com.solvd.hospital.dao.PrescriptionDao.";

    @Override
    public void create(Prescription prescription, Long doctorId, Long patientId) {
        Map<String, Object> params = new HashMap<>();
        params.put("prescription", prescription);
        params.put("doctorId", doctorId);
        params.put("patientId", patientId);
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.insert(NAMESPACE + "create", params);
            LOGGER.info("Created prescription id={}", prescription.getId());
        }
    }

    @Override
    public void update(Prescription prescription) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.update(NAMESPACE + "update", prescription);
            LOGGER.info("Updated prescription id={}", prescription.getId());
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.delete(NAMESPACE + "delete", id);
            LOGGER.info("Deleted prescription id={}", id);
        }
    }

    @Override
    public Optional<Prescription> findById(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            Prescription prescription = session.selectOne(NAMESPACE + "findById", id);
            return Optional.ofNullable(prescription);
        }
    }

    @Override
    public List<Prescription> findByPatientId(Long patientId) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.selectList(NAMESPACE + "findByPatientId", patientId);
        }
    }
}