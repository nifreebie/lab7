package org.example.server.db;

import java.sql.SQLException;

public class PostgresDatabase implements Database {
    private final String url;
    private final String login;
    private final String password;

    public PostgresDatabase(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }
    @Override
    public DAO createProductsConnection() throws SQLException {
        return new ProductDAO(url, login, password);
    }

    @Override
    public DAO createUserConnection() throws SQLException {
        return new UserDAO(url,login, password);
    }

    @Override
    public DAO createProductUserReferenceConnection() throws SQLException {
        return new ProductUserReferenceDAO(url,login, password);
    }
}
