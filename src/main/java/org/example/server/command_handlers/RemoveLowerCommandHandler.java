package org.example.server.command_handlers;

import org.example.contract.command.RemoveLowerCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utils.StatusCode;
import org.example.server.collection.CollectionManager;

public class RemoveLowerCommandHandler extends CommandHandler<RemoveLowerCommand>{
    @Override
    public Response handle(RemoveLowerCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        collectionManager.removeLower(command.getProductDTO());
        return new ResponseWithMessage(StatusCode._200_SUCCESS_, "все продукты, меньшие введенного были удалены");
    }
}
