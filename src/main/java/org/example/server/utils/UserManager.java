package org.example.server.utils;

import lombok.Getter;
import lombok.Setter;
import org.example.server.db.UserDAO;

import java.sql.SQLException;

@Getter
@Setter
public class UserManager {
    private UserDAO userDAO;

    public void signUp(String login, String password) throws SQLException {
        this.userDAO.addUser(login, password);

    }

    public void signIn(String login, String password) throws SQLException {
        this.userDAO.authenticateUser(login, password);
    }
}
