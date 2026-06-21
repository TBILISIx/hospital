package com.solvd.hospital.testfakes;

import com.solvd.hospital.dao.PaymentDao;
import com.solvd.hospital.model.Payment;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory test double for PaymentDao.
 * Lets us unit test PaymentServiceImpl without a real database or MyBatis.
 */
public class FakePaymentDao implements PaymentDao {

    private final Map<Long, Payment> store = new LinkedHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public void create(Payment payment) {
        payment.setId(idSequence.getAndIncrement());
        store.put(payment.getId(), payment);
    }

    @Override
    public void update(Payment payment) {
        store.put(payment.getId(), payment);
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Payment> findByAdmissionId(Long admissionId) {
        return store.values().stream()
                .filter(p -> p.getAdmissionId().equals(admissionId))
                .findFirst();
    }

    @Override
    public List<Payment> findUnpaid() {
        return store.values().stream()
                .filter(p -> !p.isPaid())
                .collect(Collectors.toList());
    }
}
