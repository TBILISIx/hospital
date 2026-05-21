package com.solvd.hospital.dao.impl.jdbc;

import com.solvd.hospital.dao.AbstractDao;
import com.solvd.hospital.dao.AppointmentDao;
import com.solvd.hospital.model.Appointment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCAppointmentDaoImpl extends AbstractDao implements AppointmentDao {

    private static final Logger LOGGER = LogManager.getLogger(JDBCAppointmentDaoImpl.class);

    @Override
    public void create(Appointment appointment, Long patientId, Long doctorId) {

        String sql = "INSERT INTO appointments (scheduled_at, status, notes, doctors_id, patients_id) VALUES (?, ?, ?, ?, ?)";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            preparedStatement.setTimestamp(1, Timestamp.valueOf(appointment.getScheduledAt()));
            preparedStatement.setString(2, appointment.getStatus().name());
            preparedStatement.setString(3, appointment.getNotes());
            preparedStatement.setLong(4, doctorId);
            preparedStatement.setLong(5, patientId);

            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (keys.next()) {
                appointment.setId(keys.getLong(1));
            }

            LOGGER.info("Created appointment id={}", appointment.getId());

        } catch (SQLException e) {
            LOGGER.error("Failed to create appointment", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void update(Appointment appointment) {

        String sql = "UPDATE appointments SET scheduled_at=?, status=?, notes=? WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setTimestamp(1, Timestamp.valueOf(appointment.getScheduledAt()));
            preparedStatement.setString(2, appointment.getStatus().name());
            preparedStatement.setString(3, appointment.getNotes());
            preparedStatement.setLong(4, appointment.getId());

            preparedStatement.executeUpdate();

            LOGGER.info("Updated appointment id={}", appointment.getId());

        } catch (SQLException e) {
            LOGGER.error("Failed to update appointment", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void delete(Long id) {

        String sql = "DELETE FROM appointments WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            LOGGER.info("Deleted appointment id={}", id);

        } catch (SQLException e) {
            LOGGER.error("Failed to delete appointment id={}", id, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Appointment> findById(Long id) {

        String sql = "SELECT * FROM appointments WHERE id=?";

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
            LOGGER.error("Failed to find appointment id={}", id, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return Optional.empty();
    }

    @Override
    public List<Appointment> findByPatientId(Long patientId) {

        String sql = "SELECT * FROM appointments WHERE patients_id=?";

        List<Appointment> list = new ArrayList<>();

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
            LOGGER.error("Failed to find appointments for patient id={}", patientId, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return list;
    }

    @Override
    public List<Appointment> findByDoctorId(Long doctorId) {

        String sql = "SELECT * FROM appointments WHERE doctors_id=?";

        List<Appointment> list = new ArrayList<>();

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setLong(1, doctorId);

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                list.add(mapRow(result));
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to find appointments for doctor id={}", doctorId, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return list;
    }

    private Appointment mapRow(ResultSet result) throws SQLException {

        return new Appointment(
                result.getLong("id"),
                result.getTimestamp("scheduled_at").toLocalDateTime(),
                Appointment.AppointmentStatus.valueOf(result.getString("status")),
                result.getString("notes")
        );
    }
}