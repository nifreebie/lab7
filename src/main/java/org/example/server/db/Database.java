package org.example.server.db;

import java.sql.SQLException;

public interface Database {
    DatabaseConnection createConnection() throws SQLException;
}
