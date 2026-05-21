package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.MyBatisSessionHolder;
import com.solvd.hospital.dao.PatientDao;
import com.solvd.hospital.model.Patient;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class MyBatisPatientDaoImpl implements PatientDao {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisPatientDaoImpl.class);
    private static final String NAMESPACE = "com.solvd.hospital.dao.PatientDao.";

    @Override
    public void create(Patient patient) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.insert(NAMESPACE + "create", patient);
            LOGGER.info("Created patient id={}", patient.getId());
        }
    }

    @Override
    public void update(Patient patient) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.update(NAMESPACE + "update", patient);
            LOGGER.info("Updated patient id={}", patient.getId());
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.delete(NAMESPACE + "delete", id);
            LOGGER.info("Deleted patient id={}", id);
        }
    }

    @Override
    public Optional<Patient> findById(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            Patient patient = session.selectOne(NAMESPACE + "findById", id);
            return Optional.ofNullable(patient);
        }
    }

    @Override
    public List<Patient> findAll() {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.selectList(NAMESPACE + "findAll");
        }
    }
}