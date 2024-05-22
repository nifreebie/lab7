package org.example.server.utils;

import lombok.Getter;
import lombok.Setter;
import org.example.contract.exceptions.WrongPasswordException;
import org.example.server.db.DatabaseConnection;

import java.sql.SQLException;

@Getter
@Setter
public class UserManager {
    private DatabaseConnection connection;

    public void signUp(String login, String password) throws SQLException {
        this.connection.addUser(login, password);

    }

    public void signIn(String login, String password) throws SQLException {
        if(!this.connection.authenticateUser(login, password)) throw new WrongPasswordException();
    }
}
