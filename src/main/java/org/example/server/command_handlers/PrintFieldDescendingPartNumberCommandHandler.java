package org.example.server.command_handlers;

import org.example.contract.command.PrintFieldDescendingPartNumberCommand;
import org.example.contract.model.Product;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utils.StatusCode;
import org.example.server.collection.CollectionManager;
import org.example.server.utils.PartNumberProductComparator;
import org.example.server.utils.ProductComparator;

public class PrintFieldDescendingPartNumberCommandHandler extends CommandHandler<PrintFieldDescendingPartNumberCommand>{
    @Override
    public Response handle(PrintFieldDescendingPartNumberCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        PartNumberProductComparator comparator = new PartNumberProductComparator();
        collectionManager.sort(comparator);
        String partNumber = "";
        for (Product p : collectionManager.getProducts()) {
            partNumber += p.getPartNumber() + '\n';
        }
        ProductComparator productComparator = new ProductComparator();
        collectionManager.sort(productComparator);
        return new ResponseWithMessage(StatusCode._200_SUCCESS_, partNumber);
    }
}
