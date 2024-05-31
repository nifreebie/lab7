package org.example.server.db;

import org.example.contract.exceptions.UserNotFoundException;
import org.example.contract.exceptions.WrongPasswordException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DAO {
    private final PasswordManager passwordManager = new PasswordManager();

    public UserDAO(String url, String login, String password) throws SQLException {
        super(url, login, password);
    }

    public void authenticateUser(String login, String password) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE login = ?");
        ps.setString(1, login);

        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {
            String expectedPassword = resultSet.getString("password");
            if (expectedPassword.equals(passwordManager.hashPassword(password))) {
            }else{
                throw new WrongPasswordException();
            }
        }else{
            throw new UserNotFoundException();

        }
    }


    public void addUser(String login, String password) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement("INSERT INTO users" +
                "(login, password) VALUES (?,?)");
        ps.setString(1, login);
        ps.setString(2, passwordManager.hashPassword(password));
        ps.executeUpdate();


    }
}
