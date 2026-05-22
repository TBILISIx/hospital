package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.MyBatisSessionHolder;
import com.solvd.hospital.dao.PaymentDao;
import com.solvd.hospital.model.Payment;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class MyBatisPaymentDaoImpl implements PaymentDao {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisPaymentDaoImpl.class);

    @Override
    public void create(Payment payment) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(PaymentDao.class).create(payment);
            LOGGER.info("Created payment id={}", payment.getId());
        }
    }

    @Override
    public void update(Payment payment) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(PaymentDao.class).update(payment);
            LOGGER.info("Updated payment id={}", payment.getId());
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.getMapper(PaymentDao.class).delete(id);
            LOGGER.info("Deleted payment id={}", id);
        }
    }

    @Override
    public Optional<Payment> findById(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(PaymentDao.class).findById(id);
        }
    }

    @Override
    public Optional<Payment> findByAdmissionId(Long admissionId) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(PaymentDao.class).findByAdmissionId(admissionId);
        }
    }

    @Override
    public List<Payment> findUnpaid() {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.getMapper(PaymentDao.class).findUnpaid();
        }
    }
}