package org.example.server.command_handlers;

import org.example.contract.command.RemoveGreaterCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utils.StatusCode;
import org.example.server.collection.CollectionManager;

public class RemoveGreaterCommandHandler extends CommandHandler<RemoveGreaterCommand>{
    @Override
    public Response handle(RemoveGreaterCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        collectionManager.removeGreater(command.getProductDTO());
        return new ResponseWithMessage(StatusCode._200_SUCCESS_, "все продукты, превышаюие введенный были удалены");
    }
}
