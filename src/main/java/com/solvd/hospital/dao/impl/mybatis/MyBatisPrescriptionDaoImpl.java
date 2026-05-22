package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.MyBatisSessionHolder;
import com.solvd.hospital.dao.PrescriptionDao;
import com.solvd.hospital.model.Prescription;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class MyBatisPrescriptionDaoImpl implements PrescriptionDao {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisPrescriptionDaoImpl.class);

    @Override
    public void create(Prescription prescription, Long doctorId, Long patientId) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(PrescriptionDao.class).create(prescription, doctorId, patientId);
            LOGGER.info("Created prescription id={}", prescription.getId());
        }
    }

    @Override
    public void update(Prescription prescription) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(PrescriptionDao.class).update(prescription);
            LOGGER.info("Updated prescription id={}", prescription.getId());
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(PrescriptionDao.class).delete(id);
            LOGGER.info("Deleted prescription id={}", id);
        }
    }

    @Override
    public Optional<Prescription> findById(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(PrescriptionDao.class).findById(id);
        }
    }

    @Override
    public List<Prescription> findByPatientId(Long patientId) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(PrescriptionDao.class).findByPatientId(patientId);
        }
    }
}