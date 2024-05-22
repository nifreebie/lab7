package org.example.server.db;

import org.example.contract.exceptions.UserIsNotOwnerException;
import org.example.contract.exceptions.WrongPasswordException;
import org.example.contract.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;

public abstract class DatabaseConnection {
    protected Connection connection;

    public DatabaseConnection(String url, String login, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, login, password);
    }
    public abstract boolean authenticateUser(String login, String password) throws SQLException, WrongPasswordException;
    public abstract boolean addUser(String login, String password) throws SQLException;
    public abstract int addProduct(ProductDTO productDTO, String ownerLogin) throws SQLException;
    public abstract int addAddress(Address address) throws SQLException;
    public abstract int addCoordinates(Coordinates coordinates) throws SQLException;
    public abstract int addManufacturer(Organization organization) throws SQLException;
//
    public abstract boolean updateProduct(long id, ProductDTO productDTO, String ownerLogin) throws SQLException, UserIsNotOwnerException;
//
    public abstract boolean removeById(long id, String ownerLogin) throws SQLException;
    public abstract Set<Product> getAllProducts() throws SQLException;
    public abstract int clearCollectionForUser(String ownerLogin) throws SQLException;

}
