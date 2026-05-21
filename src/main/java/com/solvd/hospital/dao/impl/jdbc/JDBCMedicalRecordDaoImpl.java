package com.solvd.hospital.dao.impl.jdbc;

import com.solvd.hospital.dao.AbstractDao;
import com.solvd.hospital.dao.MedicalRecordDao;
import com.solvd.hospital.model.BloodType;
import com.solvd.hospital.model.MedicalRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;

public class JDBCMedicalRecordDaoImpl extends AbstractDao implements MedicalRecordDao {

    private static final Logger LOGGER = LogManager.getLogger(JDBCMedicalRecordDaoImpl.class);

    @Override
    public void create(MedicalRecord record, Long patientId) {

        String sql = "INSERT INTO medical_records " +
                "(created_date, blood_type, notes, patients_id) VALUES (?, ?, ?, ?)";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            preparedStatement.setDate(1, Date.valueOf(record.getCreatedDate()));

            preparedStatement.setString(
                    2,
                    record.getBloodType() != null
                            ? record.getBloodType().name()
                            : null
            );

            preparedStatement.setString(3, record.getNotes());
            preparedStatement.setLong(4, patientId);

            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (keys.next()) {
                record.setId(keys.getLong(1));
            }

            LOGGER.info("Created medical record id={}", record.getId());

        } catch (SQLException e) {

            LOGGER.error("Failed to create medical record", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void update(MedicalRecord record) {

        String sql =
                "UPDATE medical_records SET created_date=?, blood_type=?, notes=? WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setDate(1, Date.valueOf(record.getCreatedDate()));

            preparedStatement.setString(
                    2,
                    record.getBloodType() != null ? record.getBloodType().name() : null
            );

            preparedStatement.setString(3, record.getNotes());
            preparedStatement.setLong(4, record.getId());

            preparedStatement.executeUpdate();

            LOGGER.info("Updated medical record id={}", record.getId());

        } catch (SQLException e) {

            LOGGER.error("Failed to update medical record", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void delete(Long id) {

        String sql = "DELETE FROM medical_records WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            LOGGER.info("Deleted medical record id={}", id);

        } catch (SQLException e) {

            LOGGER.error("Failed to delete medical record id={}", id, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<MedicalRecord> findById(Long id) {

        String sql = "SELECT * FROM medical_records WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setLong(1, id);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                return Optional.of(mapRow(result));
            }

        } catch (SQLException e) {

            LOGGER.error("Failed to find medical record id={}", id, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return Optional.empty();
    }

    @Override
    public Optional<MedicalRecord> findByPatientId(Long patientId) {

        String sql = "SELECT * FROM medical_records WHERE patients_id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setLong(1, patientId);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                return Optional.of(mapRow(result));
            }

        } catch (SQLException e) {

            LOGGER.error("Failed to find medical record for patient id={}", patientId, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return Optional.empty();
    }

    private MedicalRecord mapRow(ResultSet result) throws SQLException {

        String bloodType = result.getString("blood_type");

        return new MedicalRecord(
                result.getLong("id"),
                result.getDate("created_date").toLocalDate(),
                bloodType != null ? BloodType.valueOf(bloodType) : null,
                null,
                result.getString("notes")
        );
    }

}