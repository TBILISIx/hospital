package com.solvd.hospital.dao.impl.jdbc;

import com.solvd.hospital.dao.AbstractDao;
import com.solvd.hospital.dao.PaymentDao;
import com.solvd.hospital.model.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCPaymentDaoImpl extends AbstractDao implements PaymentDao {

    private static final Logger LOGGER = LogManager.getLogger(JDBCPaymentDaoImpl.class);

    @Override
    public void create(Payment payment) {

        String sql = "INSERT INTO payments (issued_date, total_amount, paid_amount, paid, admissions_id) VALUES (?, ?, ?, ?, ?)";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            preparedStatement.setDate(1, Date.valueOf(payment.getIssuedDate()));
            preparedStatement.setBigDecimal(2, payment.getTotalAmount());
            preparedStatement.setBigDecimal(3, payment.getPaidAmount());
            preparedStatement.setBoolean(4, payment.isPaid());
            preparedStatement.setLong(5, payment.getAdmissionId());

            preparedStatement.executeUpdate();

            ResultSet keys = preparedStatement.getGeneratedKeys();

            if (keys.next()) {
                payment.setId(keys.getLong(1));
            }

            LOGGER.info("Created payment id={}", payment.getId());

        } catch (SQLException e) {
            LOGGER.error("Failed to create payment", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void update(Payment payment) {

        String sql = "UPDATE payments SET issued_date=?, total_amount=?, paid_amount=?, paid=? WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setDate(1, Date.valueOf(payment.getIssuedDate()));
            preparedStatement.setBigDecimal(2, payment.getTotalAmount());
            preparedStatement.setBigDecimal(3, payment.getPaidAmount());
            preparedStatement.setBoolean(4, payment.isPaid());
            preparedStatement.setLong(5, payment.getId());

            preparedStatement.executeUpdate();

            LOGGER.info("Updated payment id={}", payment.getId());

        } catch (SQLException e) {
            LOGGER.error("Failed to update payment", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void delete(Long id) {

        String sql = "DELETE FROM payments WHERE id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            LOGGER.info("Deleted payment id={}", id);

        } catch (SQLException e) {
            LOGGER.error("Failed to delete payment id={}", id, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Payment> findById(Long id) {

        String sql = "SELECT * FROM payments WHERE id=?";

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
            LOGGER.error("Failed to find payment id={}", id, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Payment> findByAdmissionId(Long admissionId) {

        String sql = "SELECT * FROM payments WHERE admissions_id=?";

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            preparedStatement.setLong(1, admissionId);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                return Optional.of(mapRow(result));
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to find payment for admission id={}", admissionId, e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return Optional.empty();
    }

    @Override
    public List<Payment> findUnpaid() {

        String sql = "SELECT * FROM payments WHERE paid=0";

        List<Payment> list = new ArrayList<>();

        Connection connection = getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet result = preparedStatement.executeQuery()
        ) {

            while (result.next()) {
                list.add(mapRow(result));
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to find unpaid payments", e);
            throw new RuntimeException(e);

        } finally {
            releaseConnection(connection);
        }

        return list;
    }

    private Payment mapRow(ResultSet result) throws SQLException {

        return new Payment(
                result.getLong("id"),
                result.getLong("admissions_id"),
                result.getDate("issued_date").toLocalDate(),
                result.getBigDecimal("total_amount"),
                result.getBigDecimal("paid_amount"),
                result.getBoolean("paid")
        );
    }
}