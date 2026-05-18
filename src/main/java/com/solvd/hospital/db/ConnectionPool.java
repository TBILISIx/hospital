package com.solvd.hospital.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static volatile ConnectionPool instance;
    private final HikariDataSource dataSource;

    private ConnectionPool() {

        Properties properties = new Properties();

        try (
                InputStream input = ConnectionPool.class
                        .getClassLoader()
                        .getResourceAsStream("config.properties")
        ) {

            if (input == null) {
                throw new RuntimeException("config.properties not found on classpath");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }

        HikariConfig config = new HikariConfig();

        config.setDriverClassName(properties.getProperty("db.driver"));
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));

        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.pool.size", "10")));
        config.setConnectionTimeout(30_000);
        config.setPoolName("HospitalPool");

        this.dataSource = new HikariDataSource(config);

        LOGGER.info("Connection pool initialised (max size={})", config.getMaximumPoolSize());
    }

    public static ConnectionPool getInstance() {

        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }

        return instance;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void close() {

        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            LOGGER.info("Connection pool closed");
        }
    }
}