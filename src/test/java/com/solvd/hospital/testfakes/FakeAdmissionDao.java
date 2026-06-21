package com.solvd.hospital.testfakes;

import com.solvd.hospital.dao.AdmissionDao;
import com.solvd.hospital.model.Admission;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory test double for AdmissionDao.
 * Lets us unit test AdmissionServiceImpl without a real database or MyBatis.
 * Tracks the patientId link separately, the same way the real schema does
 * (Admission itself doesn't hold a patientId field).
 */
public class FakeAdmissionDao implements AdmissionDao {

    private final Map<Long, Admission> store = new LinkedHashMap<>();
    private final Map<Long, Long> patientLinks = new HashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public void create(Admission admission, Long patientId, Long roomId) {
        admission.setId(idSequence.getAndIncrement());
        store.put(admission.getId(), admission);
        patientLinks.put(admission.getId(), patientId);
    }

    @Override
    public void update(Admission admission) {
        store.put(admission.getId(), admission);
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
        patientLinks.remove(id);
    }

    @Override
    public Optional<Admission> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Admission> findByPatientId(Long patientId) {
        return store.values().stream()
                .filter(a -> patientId.equals(patientLinks.get(a.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Admission> findActive() {
        return store.values().stream()
                .filter(a -> a.getDischargedAt() == null)
                .collect(Collectors.toList());
    }
}
