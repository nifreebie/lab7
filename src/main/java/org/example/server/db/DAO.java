package org.example.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DAO {
    protected Connection connection;
    public DAO(String url, String login, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, login, password);
    }
}
