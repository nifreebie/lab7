package org.example.server.command_handlers;

import org.example.contract.command.ShowCommand;
import org.example.contract.model.Product;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utils.StatusCode;
import org.example.server.collection.CollectionManager;


public class ShowCommandHandler extends CommandHandler<ShowCommand> {
    @Override
    public Response handle(ShowCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        if(collectionManager.getSize() == 0){
            return new ResponseWithMessage(StatusCode._200_SUCCESS_, "Коллекция пуста");
        } else{
            String strCollection = "";
            for (Product p : collectionManager.getProducts()) {
               strCollection += p.toString() + '\n';
            }
            return new ResponseWithMessage(StatusCode._200_SUCCESS_, strCollection);
        }
    }
}
