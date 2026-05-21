package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.MyBatisSessionHolder;
import com.solvd.hospital.dao.AppointmentDao;
import com.solvd.hospital.model.Appointment;
import com.solvd.hospital.model.Doctor;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class MyBatisAppointmentDaoImpl implements AppointmentDao {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisAppointmentDaoImpl.class);
    private static final String NAMESPACE = "com.solvd.hospital.dao.AppointmentDao.";

    @Override
    public void create(Appointment appointment, Long patientId, Long doctorId) {
        appointment.setPatientID(patientId);
        if (appointment.getDoctor() == null) {
            Doctor doctor = new Doctor(doctorId, null, null, null, true, null, null);
            appointment.setDoctor(doctor);
        } else {
            appointment.getDoctor().setId(doctorId);
        }
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.insert(NAMESPACE + "create", appointment);
            LOGGER.info("Created appointment id={}", appointment.getId());
        }
    }

    @Override
    public void update(Appointment appointment) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.update(NAMESPACE + "update", appointment);
            LOGGER.info("Updated appointment id={}", appointment.getId());
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.delete(NAMESPACE + "delete", id);
            LOGGER.info("Deleted appointment id={}", id);
        }
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            Appointment appointment = session.selectOne(NAMESPACE + "findById", id);
            return Optional.ofNullable(appointment);
        }
    }

    @Override
    public List<Appointment> findByPatientId(Long patientId) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.selectList(NAMESPACE + "findByPatientId", patientId);
        }
    }

    @Override
    public List<Appointment> findByDoctorId(Long doctorId) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.selectList(NAMESPACE + "findByDoctorId", doctorId);
        }
    }
}