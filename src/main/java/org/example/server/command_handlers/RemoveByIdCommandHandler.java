package org.example.server.command_handlers;

import org.example.contract.command.RemoveByIdCommand;
import org.example.contract.exceptions.UserIsNotOwnerException;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utils.StatusCode;
import org.example.server.collection.CollectionManager;

import java.sql.SQLException;

public class RemoveByIdCommandHandler extends CommandHandler<RemoveByIdCommand> {

    @Override
    public Response handle(RemoveByIdCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        if(collectionManager.getSize() == 0){
            return new ResponseWithMessage(StatusCode._200_SUCCESS_, "коллекция пуста");
        } else{
            long findId = command.getId();
            if (!collectionManager.isIdExists(findId)) return new ResponseWithMessage(StatusCode._400_CLIENT_ERROR, "такого id не существует");
            else{
                try {
                    collectionManager.removeById(findId, command.getLogin());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }catch (UserIsNotOwnerException e){
                    return new ResponseWithMessage(StatusCode._400_CLIENT_ERROR, "вы не являетесь создателем продукта с id:" + findId);
                }
                return new ResponseWithMessage(StatusCode._200_SUCCESS_, "продукт с id: " + findId + " был удален");
            }
        }
    }
}
