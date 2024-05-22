package org.example.server.command_handlers;

import org.example.contract.command.AddIfMinCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utils.StatusCode;
import org.example.server.collection.CollectionManager;
import org.example.server.utils.ProductComparator;

public class AddIfMinCommandHandler extends CommandHandler<AddIfMinCommand>{
    @Override
    public Response handle(AddIfMinCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        if (collectionManager.checkIfMin(command.getProductDTO())) {
            collectionManager.add(command.getProductDTO(), command.getLogin());
            ProductComparator productComparator = new ProductComparator();
            collectionManager.sort(productComparator);
            return new ResponseWithMessage(StatusCode._200_SUCCESS_, "продукт добавлен в коллекцию");
        } else {
            return new ResponseWithMessage(StatusCode._400_CLIENT_ERROR, "введенный продукт больше наименьшего в коллекции!");
        }
    }
}
