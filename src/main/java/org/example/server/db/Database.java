package org.example.server.db;

import java.sql.SQLException;

public interface Database {
    DAO createProductsConnection() throws SQLException;
    DAO createUserConnection() throws  SQLException;
    DAO createProductUserReferenceConnection() throws SQLException;
}
