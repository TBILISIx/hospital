package com.solvd.hospital.dao.impl;

import com.solvd.hospital.dao.AbstractDao;
import com.solvd.hospital.dao.PatientDao;
import com.solvd.hospital.model.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientDaoImpl extends AbstractDao implements PatientDao {

    private static final Logger LOGGER = LogManager.getLogger(PatientDaoImpl.class);

    @Override
    public void create(Patient patient) {
        String sql = "INSERT INTO patients (first_name, last_name, date_of_birth, phone_number, insured) VALUES (?, ?, ?, ?, ?)";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getLastName());
            preparedStatement.setDate(3, Date.valueOf(patient.getDateOfBirth()));
            preparedStatement.setString(4, patient.getPhoneNumber());
            preparedStatement.setBoolean(5, patient.isInsured());

            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (keys.next()) {
                patient.setId(keys.getLong(1));
            }

            LOGGER.info("Created patient id={}", patient.getId());

        } catch (SQLException e) {
            LOGGER.error("Failed to create patient", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void update(Patient patient) {
        String sql = "UPDATE patients SET first_name=?, last_name=?, date_of_birth=?, phone_number=?, insured=? WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, patient.getFirstName());
            preparedStatement.setString(2, patient.getLastName());
            preparedStatement.setDate(3, Date.valueOf(patient.getDateOfBirth()));
            preparedStatement.setString(4, patient.getPhoneNumber());
            preparedStatement.setBoolean(5, patient.isInsured());
            preparedStatement.setLong(6, patient.getId());

            preparedStatement.executeUpdate();

            LOGGER.info("Updated patient id={}", patient.getId());

        } catch (SQLException e) {
            LOGGER.error("Failed to update patient", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM patients WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            LOGGER.info("Deleted patient id={}", id);

        } catch (SQLException e) {
            LOGGER.error("Failed to delete patient id={}", id, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Patient> findById(Long id) {
        String sql = "SELECT * FROM patients WHERE id=?";

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
            LOGGER.error("Failed to find patient id={}", id, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return Optional.empty();
    }

    @Override
    public List<Patient> findAll() {
        String sql = "SELECT * FROM patients";

        List<Patient> list = new ArrayList<>();

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet result = preparedStatement.executeQuery()
        ) {
            while (result.next()) {
                list.add(mapRow(result));
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to find all patients", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return list;
    }

    private Patient mapRow(ResultSet result) throws SQLException {
        return new Patient(
                result.getLong("id"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getDate("date_of_birth").toLocalDate(),
                result.getString("phone_number"),
                result.getBoolean("insured"),
                null,
                null,
                null,
                null
        );
    }

}