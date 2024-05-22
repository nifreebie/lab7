package org.example.server.command_handlers;

import org.example.contract.command.InfoCommand;
import org.example.contract.model.Product;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utils.StatusCode;
import org.example.server.collection.CollectionManager;

public class InfoCommandHandler extends CommandHandler<InfoCommand>{
    @Override
    public Response handle(InfoCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        String infoStr = "";
        infoStr += "информация о коллекции:\n";
        infoStr += "тип коллекции: " + collectionManager.getProducts().getClass().getSimpleName() + "\n";
        infoStr += "тип элементов колекции: " + Product.class.getSimpleName() + "\n";
        infoStr += "количество элементов коллекции: " + collectionManager.getSize() + "\n";
        return new ResponseWithMessage(StatusCode._200_SUCCESS_, infoStr);
    }
}
