package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.util.MyBatisSessionHolder;
import com.solvd.hospital.dao.PatientDao;
import com.solvd.hospital.model.Patient;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class MyBatisPatientDaoImpl implements PatientDao {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisPatientDaoImpl.class);

    @Override
    public void create(Patient patient) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(PatientDao.class).create(patient);
            LOGGER.info("Created patient id={}", patient.getId());
        }
    }

    @Override
    public void update(Patient patient) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(PatientDao.class).update(patient);
            LOGGER.info("Updated patient id={}", patient.getId());
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(PatientDao.class).delete(id);
            LOGGER.info("Deleted patient id={}", id);
        }
    }

    @Override
    public Optional<Patient> findById(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(PatientDao.class).findById(id);
        }
    }

    @Override
    public List<Patient> findAll() {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(PatientDao.class).findAll();
        }
    }
}