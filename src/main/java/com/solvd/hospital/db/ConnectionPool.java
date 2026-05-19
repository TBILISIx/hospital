package com.solvd.hospital.db;

import com.solvd.hospital.enums.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;

    private final List<Connection> connections;

    private ConnectionPool() {
        try {
            Class.forName(Config.DRIVER.getValue());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to find Driver class.", e);
        }

        int poolSize = Integer.parseInt(Config.POOL_SIZE.getValue());
        this.connections = new ArrayList<>(poolSize);

        IntStream.range(0, poolSize)
                .boxed()
                .forEach(index -> connections.add(createConnection()));

        LOGGER.info("Connection pool initialized with {} connections", poolSize);
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public synchronized Connection getConnection() {
        for (Connection connection : connections) {
            try {
                if (connection.getAutoCommit()) {
                    return connection;
                }
            } catch (SQLException e) {
                LOGGER.error("Error checking connection state", e);
            }
        }
        throw new RuntimeException("No free connections available");
    }

    public synchronized void releaseConnection(Connection connection) {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error("Failed to release connection", e);
        }
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(
                    Config.URL.getValue(),
                    Config.USERNAME.getValue(),
                    Config.PASSWORD.getValue()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create connection", e);
        }
    }

    public void close() {
        connections.forEach(conn -> {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("Failed to close connection", e);
            }
        });
        connections.clear();
        LOGGER.info("Connection pool closed");
    }
}