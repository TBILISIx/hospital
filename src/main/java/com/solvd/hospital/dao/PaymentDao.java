package com.solvd.hospital.dao;

import com.solvd.hospital.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentDao {

    void create(Payment payment);

    void update(Payment payment);

    void delete(Long id);

    Optional<Payment> findById(Long id);

    Optional<Payment> findByAdmissionId(Long admissionId);

    List<Payment> findUnpaid();

}
