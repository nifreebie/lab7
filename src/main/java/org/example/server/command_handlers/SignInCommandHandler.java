package org.example.server.command_handlers;

import org.example.contract.command.SignInCommand;
import org.example.contract.exceptions.UserNotFoundException;
import org.example.contract.exceptions.WrongPasswordException;
import org.example.contract.responses.AuthorizationResponse;
import org.example.contract.responses.Response;
import org.example.contract.utils.StatusCode;
import org.example.server.Server;
import org.example.server.utils.ServerAppContainer;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class SignInCommandHandler extends CommandHandler<SignInCommand> {
    @Override
    public Response handle(SignInCommand command) {
        try {
            ServerAppContainer.getInstance().getUserManager().signIn(command.getLogin(), command.getPassword());
            return new AuthorizationResponse(StatusCode._200_SUCCESS_, "", command.getLogin(), command.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException();
        }catch(WrongPasswordException e){
            return new AuthorizationResponse(StatusCode._400_CLIENT_ERROR, "неверный пароль", null, null);
        }catch(UserNotFoundException e){
            return new AuthorizationResponse(StatusCode._400_CLIENT_ERROR, "пользователя с таким логином не существует", null, null);
        }
    }
}
