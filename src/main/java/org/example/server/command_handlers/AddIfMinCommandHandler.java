package org.example.server.command_handlers;

import org.example.contract.command.AddIfMinCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utils.StatusCode;
import org.example.server.collection.CollectionManager;
import org.example.server.utils.ProductComparator;

import java.sql.SQLException;

public class AddIfMinCommandHandler extends CommandHandler<AddIfMinCommand>{
    @Override
    public Response handle(AddIfMinCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        if (collectionManager.checkIfMin(command.getProductDTO())) {
            try {
                collectionManager.add(command.getProductDTO(), command.getUser().getLogin());
                ProductComparator productComparator = new ProductComparator();
                collectionManager.sort(productComparator);
                return new ResponseWithMessage(StatusCode._200_SUCCESS_, "продукт добавлен в коллекцию");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {
            return new ResponseWithMessage(StatusCode._400_CLIENT_ERROR, "введенный продукт больше наименьшего в коллекции!");
        }
    }
}
