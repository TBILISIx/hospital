package com.solvd.hospital.dao.impl;

import com.solvd.hospital.dao.AbstractDao;
import com.solvd.hospital.dao.ReportDao;
import com.solvd.hospital.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReportDaoImpl extends AbstractDao implements ReportDao {

    private static final Logger LOGGER =
            LogManager.getLogger(ReportDaoImpl.class);


    @Override
    public List<Patient> findPatientsWithAdmissionDetails() {

        String sql =
                "SELECT " +
                        "p.id AS patient_id, " +
                        "p.first_name, " +
                        "p.last_name, " +
                        "p.date_of_birth, " +
                        "p.phone_number, " +
                        "p.insured, " +

                        "mr.id AS medical_record_id, " +
                        "mr.created_date, " +
                        "mr.blood_type, " +
                        "mr.notes AS mr_notes, " +

                        "al.id AS allergy_id, " +
                        "al.name AS allergy_name, " +

                        "adm.id AS admission_id, " +
                        "adm.admitted_at, " +
                        "adm.discharged_at, " +
                        "adm.reason, " +

                        "pay.id AS payment_id, " +
                        "pay.issued_date, " +
                        "pay.total_amount, " +
                        "pay.paid_amount, " +
                        "pay.paid " +

                        "FROM patients p " +
                        "INNER JOIN admissions adm ON adm.patients_id = p.id " +
                        "LEFT JOIN payments pay ON pay.admissions_id = adm.id " +
                        "LEFT JOIN medical_records mr ON mr.patients_id = p.id " +
                        "LEFT JOIN allergies al ON al.medical_records_id = mr.id " +
                        "ORDER BY p.id, adm.id, al.id";

        Map<Long, Patient> patientMap = new LinkedHashMap<>();
        Map<Long, MedicalRecord> recordMap = new HashMap<>();

        Connection connection = getConnection();

        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                long patientId = rs.getLong("patient_id");

                if (!patientMap.containsKey(patientId)) {

                    Payment payment = mapPayment(rs);

                    Admission admission = new Admission(
                            rs.getLong("admission_id"),
                            rs.getTimestamp("admitted_at").toLocalDateTime(),
                            rs.getTimestamp("discharged_at") != null
                                    ? rs.getTimestamp("discharged_at").toLocalDateTime()
                                    : null,
                            rs.getString("reason"),
                            payment
                    );

                    MedicalRecord medicalRecord = null;

                    long mrId = rs.getLong("medical_record_id");

                    if (!rs.wasNull()) {

                        String bloodTypeStr = rs.getString("blood_type");

                        medicalRecord = new MedicalRecord(
                                mrId,
                                rs.getDate("created_date").toLocalDate(),
                                bloodTypeStr != null
                                        ? BloodType.valueOf(bloodTypeStr)
                                        : null,
                                new ArrayList<>(),
                                rs.getString("mr_notes")
                        );

                        recordMap.put(patientId, medicalRecord);
                    }

                    Patient patient = new Patient(
                            patientId,
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDate("date_of_birth").toLocalDate(),
                            rs.getString("phone_number"),
                            rs.getBoolean("insured"),
                            medicalRecord,
                            null,
                            admission,
                            null
                    );

                    patientMap.put(patientId, patient);
                }

                MedicalRecord mr = recordMap.get(patientId);

                if (mr != null) {

                    long allergyId = rs.getLong("allergy_id");

                    if (!rs.wasNull()) {

                        boolean alreadyAdded = mr.getAllergies()
                                .stream()
                                .anyMatch(a -> a.getId().equals(allergyId));

                        if (!alreadyAdded) {

                            mr.getAllergies().add(
                                    new Allergy(
                                            allergyId,
                                            rs.getString("allergy_name")
                                    )
                            );
                        }
                    }
                }
            }

        } catch (SQLException e) {

            LOGGER.error("Failed to run patient admission report", e);
            throw new RuntimeException(e);

        } finally {

            releaseConnection(connection);
        }

        List<Patient> result = new ArrayList<>(patientMap.values());

        LOGGER.info("Patient admission report: {} patients", result.size());

        return result;
    }

    // helper method
    private Payment mapPayment(ResultSet rs) throws SQLException {

        long paymentId = rs.getLong("payment_id");

        if (rs.wasNull()) {
            return null;
        }

        return new Payment(
                paymentId,
                rs.getLong("admission_id"),
                rs.getDate("issued_date").toLocalDate(),
                rs.getBigDecimal("total_amount"),
                rs.getBigDecimal("paid_amount"),
                rs.getBoolean("paid")
        );
    }

}