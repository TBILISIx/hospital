package com.solvd.hospital.dao.impl;

import com.solvd.hospital.dao.AbstractDao;
import com.solvd.hospital.dao.AdmissionDao;
import com.solvd.hospital.model.Admission;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdmissionDaoImpl extends AbstractDao implements AdmissionDao {

    private static final Logger LOGGER = LogManager.getLogger(AdmissionDaoImpl.class);

    @Override
    public void create(Admission admission, Long patientId, Long roomId) {

        String sql = "INSERT INTO admissions (admitted_at, discharged_at, reason, rooms_id, patients_id) VALUES (?, ?, ?, ?, ?)";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            preparedStatement.setTimestamp(1, Timestamp.valueOf(admission.getAdmittedAt()));

            if (admission.getDischargedAt() != null) {
                preparedStatement.setTimestamp(2, Timestamp.valueOf(admission.getDischargedAt()));
            } else {
                preparedStatement.setNull(2, Types.TIMESTAMP);
            }

            preparedStatement.setString(3, admission.getReason());
            preparedStatement.setLong(4, roomId);
            preparedStatement.setLong(5, patientId);

            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (keys.next()) {
                admission.setId(keys.getLong(1));
            }

            LOGGER.info("Created admission id={}", admission.getId());

        } catch (SQLException e) {
            LOGGER.error("Failed to create admission", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void update(Admission admission) {

        String sql = "UPDATE admissions SET admitted_at=?, discharged_at=?, reason=? WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setTimestamp(1, Timestamp.valueOf(admission.getAdmittedAt()));

            if (admission.getDischargedAt() != null) {
                preparedStatement.setTimestamp(2, Timestamp.valueOf(admission.getDischargedAt()));
            } else {
                preparedStatement.setNull(2, Types.TIMESTAMP);
            }

            preparedStatement.setString(3, admission.getReason());
            preparedStatement.setLong(4, admission.getId());

            preparedStatement.executeUpdate();

            LOGGER.info("Updated admission id={}", admission.getId());

        } catch (SQLException e) {
            LOGGER.error("Failed to update admission", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void delete(Long id) {

        String sql = "DELETE FROM admissions WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            LOGGER.info("Deleted admission id={}", id);

        } catch (SQLException e) {
            LOGGER.error("Failed to delete admission id={}", id, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Admission> findById(Long id) {

        String sql = "SELECT * FROM admissions WHERE id=?";

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
            LOGGER.error("Failed to find admission id={}", id, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return Optional.empty();
    }

    @Override
    public List<Admission> findByPatientId(Long patientId) {

        String sql = "SELECT * FROM admissions WHERE patients_id=?";

        List<Admission> list = new ArrayList<>();

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setLong(1, patientId);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                list.add(mapRow(result));
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to find admissions for patient id={}", patientId, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return list;
    }

    @Override
    public List<Admission> findActive() {

        String sql = "SELECT * FROM admissions WHERE discharged_at IS NULL";

        List<Admission> list = new ArrayList<>();

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet result = preparedStatement.executeQuery()
        ) {

            while (result.next()) {
                list.add(mapRow(result));
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to find active admissions", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return list;
    }

    private Admission mapRow(ResultSet result) throws SQLException {

        Timestamp discharged = result.getTimestamp("discharged_at");

        return new Admission(
                result.getLong("id"),
                result.getTimestamp("admitted_at").toLocalDateTime(),
                discharged != null ? discharged.toLocalDateTime() : null,
                result.getString("reason"),
                null
        );
    }
}