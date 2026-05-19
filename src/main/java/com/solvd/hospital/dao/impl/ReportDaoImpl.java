package com.solvd.hospital.dao.impl;

import com.solvd.hospital.dao.AbstractDao;
import com.solvd.hospital.dao.ReportDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDaoImpl extends AbstractDao implements ReportDao {

    private static final Logger LOGGER = LogManager.getLogger(ReportDaoImpl.class);

    @Override
    public List<PatientAdmissionReport> findPatientAdmissionReport() {

        String sql =
                "SELECT " +
                        "p.id AS patient_id, " +
                        "p.first_name AS patient_first_name, " +
                        "p.last_name AS patient_last_name, " +
                        "mr.blood_type, " +
                        "adm.id AS admission_id, " +
                        "adm.reason AS admission_reason, " +
                        "adm.admitted_at, " +
                        "adm.discharged_at, " +
                        "rm.room_number, " +
                        "rm.type AS room_type, " +
                        "dep.name AS department_name, " +
                        "mc.name AS clinic_name, " +
                        "pay.total_amount, " +
                        "pay.paid_amount, " +
                        "pay.paid " +
                        "FROM patients p " +
                        "INNER JOIN admissions adm ON adm.patients_id = p.id " +
                        "INNER JOIN rooms rm ON rm.id = adm.rooms_id " +
                        "INNER JOIN departments dep ON dep.id = rm.departments_id " +
                        "INNER JOIN medical_clinics mc ON mc.id = dep.medical_clinics_id " +
                        "LEFT JOIN payments pay ON pay.admissions_id = adm.id " +
                        "LEFT JOIN medical_records mr ON mr.patients_id = p.id " +
                        "ORDER BY p.id";

        List<PatientAdmissionReport> results = new ArrayList<>();

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet result = preparedStatement.executeQuery()
        ) {

            while (result.next()) {
                results.add(mapRow(result));
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to run patient admission report", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        LOGGER.info("Patient admission report: {} rows", results.size());

        return results;
    }

    private PatientAdmissionReport mapRow(ResultSet result) throws SQLException {

        PatientAdmissionReport row = new PatientAdmissionReport();

        row.setPatientId(result.getLong("patient_id"));
        row.setPatientFirstName(result.getString("patient_first_name"));
        row.setPatientLastName(result.getString("patient_last_name"));
        row.setBloodType(result.getString("blood_type"));

        row.setAdmissionId(result.getLong("admission_id"));
        row.setAdmissionReason(result.getString("admission_reason"));
        row.setAdmittedAt(String.valueOf(result.getTimestamp("admitted_at")));

        Timestamp discharged = result.getTimestamp("discharged_at");
        row.setDischargedAt(discharged != null
                ? discharged.toString()
                : "still admitted");

        row.setRoomNumber(result.getString("room_number"));
        row.setRoomType(result.getString("room_type"));
        row.setDepartmentName(result.getString("department_name"));
        row.setClinicName(result.getString("clinic_name"));

        row.setTotalAmount(String.valueOf(result.getBigDecimal("total_amount")));
        row.setPaidAmount(String.valueOf(result.getBigDecimal("paid_amount")));
        row.setPaid(result.getBoolean("paid"));

        return row;
    }
}