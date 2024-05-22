package org.example.server.command_handlers;

import org.example.contract.command.PrintDescendingCommand;
import org.example.contract.model.Product;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utils.StatusCode;
import org.example.server.collection.CollectionManager;
import org.example.server.utils.ProductComparator;
import org.example.server.utils.ReverseProductComparator;

public class PrintDescendingCommandHandler extends CommandHandler<PrintDescendingCommand>{
    @Override
    public Response handle(PrintDescendingCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        ReverseProductComparator reverseProductComparator = new ReverseProductComparator();
        collectionManager.sort(reverseProductComparator);
        String strCollection = "";
        for (Product p : collectionManager.getProducts()) {
            strCollection += p.toString() + '\n';
        }
        ProductComparator productComparator = new ProductComparator();
        collectionManager.sort(productComparator);
        return new ResponseWithMessage(StatusCode._200_SUCCESS_, strCollection);
    }
}
