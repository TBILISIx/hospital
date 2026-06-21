package com.solvd.hospital.testfakes;

import com.solvd.hospital.dao.DoctorDao;
import com.solvd.hospital.model.Doctor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory test double for DoctorDao.
 * Lets us unit test DoctorServiceImpl without a real database or MyBatis.
 */
public class FakeDoctorDao implements DoctorDao {

    private final Map<Long, Doctor> store = new LinkedHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public void create(Doctor doctor) {
        doctor.setId(idSequence.getAndIncrement());
        store.put(doctor.getId(), doctor);
    }

    @Override
    public void update(Doctor doctor) {
        store.put(doctor.getId(), doctor);
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Doctor> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Doctor> findByAvailability(boolean available) {
        return store.values().stream()
                .filter(d -> d.isAvailable() == available)
                .collect(Collectors.toList());
    }
}
