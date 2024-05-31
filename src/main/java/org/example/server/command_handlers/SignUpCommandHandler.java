package org.example.server.command_handlers;

import org.example.contract.command.SignUpCommand;
import org.example.contract.responses.AuthorizationResponse;
import org.example.contract.responses.Response;
import org.example.contract.utils.StatusCode;
import org.example.server.utils.ServerAppContainer;

import java.sql.SQLException;
import java.sql.SQLOutput;

public class SignUpCommandHandler extends CommandHandler<SignUpCommand> {
    @Override
    public Response handle(SignUpCommand command) {
        try {
            ServerAppContainer.getInstance().getUserManager().signUp(command.getUser().getLogin(), command.getUser().getPassword());
            return new AuthorizationResponse(StatusCode._200_SUCCESS_, "", command.getUser());
        } catch (SQLException e) {
            return new AuthorizationResponse(StatusCode._400_CLIENT_ERROR, "пользователь с таким логином уже есть", null);
        }
    }
}
