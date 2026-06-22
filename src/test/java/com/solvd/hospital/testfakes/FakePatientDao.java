package com.solvd.hospital.testfakes;

import com.solvd.hospital.dao.PatientDao;
import com.solvd.hospital.model.Patient;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FakePatientDao implements PatientDao {

    private final Map<Long, Patient> store = new LinkedHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    private int createCallCount = 0;
    private int updateCallCount = 0;
    private int deleteCallCount = 0;

    @Override
    public void create(Patient patient) {
        createCallCount++;
        patient.setId(idSequence.getAndIncrement());
        store.put(patient.getId(), patient);
    }

    @Override
    public void update(Patient patient) {
        updateCallCount++;
        store.put(patient.getId(), patient);
    }

    @Override
    public void delete(Long id) {
        deleteCallCount++;
        store.remove(id);
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Patient> findAll() {
        return new ArrayList<>(store.values());
    }

    public int getCreateCallCount() {
        return createCallCount;
    }

    public int getUpdateCallCount() {
        return updateCallCount;
    }

    public int getDeleteCallCount() {
        return deleteCallCount;
    }

}
