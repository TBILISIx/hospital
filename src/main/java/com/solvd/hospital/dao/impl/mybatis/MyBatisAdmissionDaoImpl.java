package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.MyBatisSessionHolder;
import com.solvd.hospital.dao.AdmissionDao;
import com.solvd.hospital.model.Admission;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MyBatisAdmissionDaoImpl implements AdmissionDao {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisAdmissionDaoImpl.class);
    private static final String NAMESPACE = "com.solvd.hospital.dao.AdmissionDao.";

    @Override
    public void create(Admission admission, Long patientId, Long roomId) {
        Map<String, Object> params = new HashMap<>();
        params.put("admission", admission);
        params.put("patientId", patientId);
        params.put("roomId", roomId);
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.insert(NAMESPACE + "create", params);
            LOGGER.info("Created admission id={}", admission.getId());
        }
    }

    @Override
    public void update(Admission admission) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.update(NAMESPACE + "update", admission);
            LOGGER.info("Updated admission id={}", admission.getId());
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.delete(NAMESPACE + "delete", id);
            LOGGER.info("Deleted admission id={}", id);
        }
    }

    @Override
    public Optional<Admission> findById(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            Admission admission = session.selectOne(NAMESPACE + "findById", id);
            return Optional.ofNullable(admission);
        }
    }

    @Override
    public List<Admission> findByPatientId(Long patientId) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.selectList(NAMESPACE + "findByPatientId", patientId);
        }
    }

    @Override
    public List<Admission> findActive() {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.selectList(NAMESPACE + "findActive");
        }
    }
}
