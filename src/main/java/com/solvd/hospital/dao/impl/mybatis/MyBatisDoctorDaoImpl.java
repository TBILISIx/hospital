package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.MyBatisSessionHolder;
import com.solvd.hospital.dao.DoctorDao;
import com.solvd.hospital.model.Doctor;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class MyBatisDoctorDaoImpl implements DoctorDao {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisDoctorDaoImpl.class);

    @Override
    public void create(Doctor doctor) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(DoctorDao.class).create(doctor);
            LOGGER.info("Created doctor id={}", doctor.getId());
        }
    }

    @Override
    public void update(Doctor doctor) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(DoctorDao.class).update(doctor);
            LOGGER.info("Updated doctor id={}", doctor.getId());
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(DoctorDao.class).delete(id);
            LOGGER.info("Deleted doctor id={}", id);
        }
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(DoctorDao.class).findById(id);
        }
    }

    @Override
    public List<Doctor> findAll() {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(DoctorDao.class).findAll();
        }
    }

    @Override
    public List<Doctor> findByAvailability(boolean available) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(DoctorDao.class).findByAvailability(available);
        }
    }
}