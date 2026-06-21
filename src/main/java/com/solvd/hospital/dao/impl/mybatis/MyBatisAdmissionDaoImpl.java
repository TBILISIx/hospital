package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.util.MyBatisSessionHolder;
import com.solvd.hospital.dao.AdmissionDao;
import com.solvd.hospital.model.Admission;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class MyBatisAdmissionDaoImpl implements AdmissionDao {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisAdmissionDaoImpl.class);

    @Override
    public void create(Admission admission, Long patientId, Long roomId) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(AdmissionDao.class).create(admission, patientId, roomId);
            LOGGER.info("Created admission id={}", admission.getId());
        }
    }

    @Override
    public void update(Admission admission) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(AdmissionDao.class).update(admission);
            LOGGER.info("Updated admission id={}", admission.getId());
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(AdmissionDao.class).delete(id);
            LOGGER.info("Deleted admission id={}", id);
        }
    }

    @Override
    public Optional<Admission> findById(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(AdmissionDao.class).findById(id);
        }
    }

    @Override
    public List<Admission> findByPatientId(Long patientId) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(AdmissionDao.class).findByPatientId(patientId);
        }
    }

    @Override
    public List<Admission> findActive() {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(AdmissionDao.class).findActive();
        }
    }
}