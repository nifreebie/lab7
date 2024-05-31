package org.example.server.command_handlers;

import org.example.contract.command.UpdateCommand;
import org.example.contract.exceptions.UserIsNotOwnerException;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utils.StatusCode;
import org.example.server.collection.CollectionManager;

import java.sql.SQLException;

public class UpdateCommandHandler extends CommandHandler<UpdateCommand> {
    @Override
    public Response handle(UpdateCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        if (collectionManager.getSize() == 0) {
            return new ResponseWithMessage(StatusCode._200_SUCCESS_, "коллекция пустая");
        }else{
            long updateId = command.getId();
            if (!collectionManager.isIdExists(updateId)) {
                return new ResponseWithMessage(StatusCode._400_CLIENT_ERROR,"такого id не существует!" );
            }else{
                try {
                    collectionManager.updateById(updateId, command.getProductDTO(), command.getUser().getLogin());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }catch(UserIsNotOwnerException e){
                    return new ResponseWithMessage(StatusCode._400_CLIENT_ERROR, "вы не являетесь создателем продукта с id:" + updateId);
                }

            }
            return new ResponseWithMessage(StatusCode._200_SUCCESS_, "продукт с id: " + updateId + " был удален");
        }
    }
}
