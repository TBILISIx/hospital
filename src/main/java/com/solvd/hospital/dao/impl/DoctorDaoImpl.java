package com.solvd.hospital.dao.impl;

import com.solvd.hospital.dao.AbstractDao;
import com.solvd.hospital.dao.DoctorDao;
import com.solvd.hospital.model.Doctor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorDaoImpl extends AbstractDao implements DoctorDao {

    private static final Logger LOGGER = LogManager.getLogger(DoctorDaoImpl.class);

    @Override
    public void create(Doctor doctor) {

        String sql = "INSERT INTO doctors (first_name, last_name, specialization, available, departments_id) VALUES (?, ?, ?, ?, ?)";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            preparedStatement.setString(1, doctor.getFirstName());
            preparedStatement.setString(2, doctor.getLastName());
            preparedStatement.setString(3, doctor.getSpecialization());
            preparedStatement.setBoolean(4, doctor.isAvailable());
            preparedStatement.setLong(5, doctor.getDepartmentId());

            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (keys.next()) {
                doctor.setId(keys.getLong(1));
            }

            LOGGER.info("Created doctor id={}", doctor.getId());

        } catch (SQLException e) {
            LOGGER.error("Failed to create doctor", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void update(Doctor doctor) {

        String sql = "UPDATE doctors SET first_name=?, last_name=?, specialization=?, available=?, departments_id=? WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setString(1, doctor.getFirstName());
            preparedStatement.setString(2, doctor.getLastName());
            preparedStatement.setString(3, doctor.getSpecialization());
            preparedStatement.setBoolean(4, doctor.isAvailable());
            preparedStatement.setLong(5, doctor.getDepartmentId());
            preparedStatement.setLong(6, doctor.getId());

            preparedStatement.executeUpdate();

            LOGGER.info("Updated doctor id={}", doctor.getId());

        } catch (SQLException e) {
            LOGGER.error("Failed to update doctor", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void delete(Long id) {

        String sql = "DELETE FROM doctors WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            LOGGER.info("Deleted doctor id={}", id);

        } catch (SQLException e) {
            LOGGER.error("Failed to delete doctor id={}", id, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Doctor> findById(Long id) {

        String sql = "SELECT * FROM doctors WHERE id=?";

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
            LOGGER.error("Failed to find doctor id={}", id, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return Optional.empty();
    }

    @Override
    public List<Doctor> findAll() {

        String sql = "SELECT * FROM doctors";

        List<Doctor> list = new ArrayList<>();

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet result = preparedStatement.executeQuery()
        ) {

            while (result.next()) {
                list.add(mapRow(result));
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to find all doctors", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return list;
    }

    @Override
    public List<Doctor> findByAvailability(boolean available) {

        String sql = "SELECT * FROM doctors WHERE available=?";

        List<Doctor> list = new ArrayList<>();

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setBoolean(1, available);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                list.add(mapRow(result));
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to find doctors by availability", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return list;
    }

    private Doctor mapRow(ResultSet result) throws SQLException {

        Doctor doctor = new Doctor(
                result.getLong("id"),
                result.getString("first_name"),
                result.getString("last_name"),
                result.getString("specialization"),
                result.getBoolean("available"),
                null,
                null
        );

        doctor.setDepartmentId(result.getLong("departments_id"));

        return doctor;
    }
}