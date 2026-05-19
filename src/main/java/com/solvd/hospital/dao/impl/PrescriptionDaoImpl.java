package com.solvd.hospital.dao.impl;

import com.solvd.hospital.dao.AbstractDao;
import com.solvd.hospital.dao.PrescriptionDao;
import com.solvd.hospital.model.Prescription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrescriptionDaoImpl extends AbstractDao implements PrescriptionDao {

    private static final Logger LOGGER = LogManager.getLogger(PrescriptionDaoImpl.class);

    @Override
    public void create(Prescription prescription, Long doctorId, Long patientId) {

        String sql = "INSERT INTO prescriptions (issued_date, instructions, doctors_id, patients_id) VALUES (?, ?, ?, ?)";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            preparedStatement.setDate(1, Date.valueOf(prescription.getIssuedDate()));
            preparedStatement.setString(2, prescription.getInstructions());
            preparedStatement.setLong(3, doctorId);
            preparedStatement.setLong(4, patientId);

            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (keys.next()) {
                prescription.setId(keys.getLong(1));
            }

            LOGGER.info("Created prescription id={}", prescription.getId());

        } catch (SQLException e) {
            LOGGER.error("Failed to create prescription", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void update(Prescription prescription) {

        String sql = "UPDATE prescriptions SET issued_date=?, instructions=? WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setDate(1, Date.valueOf(prescription.getIssuedDate()));
            preparedStatement.setString(2, prescription.getInstructions());
            preparedStatement.setLong(3, prescription.getId());

            preparedStatement.executeUpdate();

            LOGGER.info("Updated prescription id={}", prescription.getId());

        } catch (SQLException e) {
            LOGGER.error("Failed to update prescription", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void delete(Long id) {

        String sql = "DELETE FROM prescriptions WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            LOGGER.info("Deleted prescription id={}", id);

        } catch (SQLException e) {
            LOGGER.error("Failed to delete prescription id={}", id, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Prescription> findById(Long id) {

        String sql = "SELECT * FROM prescriptions WHERE id=?";

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
            LOGGER.error("Failed to find prescription id={}", id, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return Optional.empty();
    }

    @Override
    public List<Prescription> findByPatientId(Long patientId) {

        String sql = "SELECT * FROM prescriptions WHERE patients_id=?";

        List<Prescription> list = new ArrayList<>();

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
            LOGGER.error("Failed to find prescriptions for patient id={}", patientId, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return list;
    }

    private Prescription mapRow(ResultSet result) throws SQLException {

        return new Prescription(
                result.getLong("id"),
                result.getDate("issued_date").toLocalDate(),
                null,
                result.getString("instructions")
        );
    }
}