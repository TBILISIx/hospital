package com.solvd.hospital.dao;

import com.solvd.hospital.db.ConnectionPool;

import java.sql.Connection;

public abstract class AbstractDao {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    protected Connection getConnection() {
        return CONNECTION_POOL.getConnection();
    }

    protected void releaseConnection(Connection connection) {
        CONNECTION_POOL.releaseConnection(connection);
    }
}