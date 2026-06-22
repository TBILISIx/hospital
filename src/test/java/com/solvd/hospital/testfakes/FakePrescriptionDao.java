package com.solvd.hospital.testfakes;

import com.solvd.hospital.dao.PrescriptionDao;
import com.solvd.hospital.model.Prescription;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FakePrescriptionDao implements PrescriptionDao {

    private final Map<Long, Prescription> store = new LinkedHashMap<>();
    private final Map<Long, Long> patientLinks = new HashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public void create(Prescription prescription, Long doctorId, Long patientId) {
        prescription.setId(idSequence.getAndIncrement());
        store.put(prescription.getId(), prescription);
        patientLinks.put(prescription.getId(), patientId);
    }

    @Override
    public void update(Prescription prescription) {
        store.put(prescription.getId(), prescription);
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
        patientLinks.remove(id);
    }

    @Override
    public Optional<Prescription> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Prescription> findByPatientId(Long patientId) {
        return store.values().stream()
                .filter(p -> patientId.equals(patientLinks.get(p.getId())))
                .collect(Collectors.toList());
    }

}
