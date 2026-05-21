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
    private static final String NAMESPACE = "com.solvd.hospital.dao.mybatis.MyBatisPaymentDao.";

    @Override
    public void create(Payment payment) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.insert(NAMESPACE + "create", payment);
            LOGGER.info("Created payment id={}", payment.getId());
        }
    }

    @Override
    public void update(Payment payment) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.update(NAMESPACE + "update", payment);
            LOGGER.info("Updated payment id={}", payment.getId());
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            session.delete(NAMESPACE + "delete", id);
            LOGGER.info("Deleted payment id={}", id);
        }
    }

    @Override
    public Optional<Payment> findById(Long id) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            Payment payment = session.selectOne(NAMESPACE + "findById", id);
            return Optional.ofNullable(payment);
        }
    }

    @Override
    public Optional<Payment> findByAdmissionId(Long admissionId) {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            Payment payment = session.selectOne(NAMESPACE + "findByAdmissionId", admissionId);
            return Optional.ofNullable(payment);
        }
    }

    @Override
    public List<Payment> findUnpaid() {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            return session.selectList(NAMESPACE + "findUnpaid");
        }
    }
}
