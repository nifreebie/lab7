package org.example.server;

import org.example.contract.model.Product;
import org.example.server.collection.CollectionManager;
import org.example.server.db.*;
import org.example.server.utils.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;



public class Server {
    public static void main(String[] args) throws IOException {
        initServerAppContainer();
        initCollection();
        Logger logger = new Logger("logs.log");
        RequestHandler requestHandler = new RequestHandler(ServerAppContainer.getInstance().getCommandManager(), logger);
        TCPserver server = new TCPserver(ServerAppContainer.getInstance().getCommandManager(), requestHandler, logger);
        try {
            server.openConnection();
            server.run();
        } finally {
            server.close();
        }
    }
    private static void initServerAppContainer(){
        ServerAppContainer app = ServerAppContainer.getInstance();
        CommandManager commandManager = new CommandManager();
        app.setCommandManager(commandManager);

    }
    private static void initCollection(){
        Database db = new PostgresDatabase("jdbc:postgresql://localhost:5432/lab7db", "nifreebie", "1234");
        try {
            DAO productDAO = db.createProductsConnection();
            DAO userDAO = db.createUserConnection();
            DAO productUserReferenceDAO = db.createProductUserReferenceConnection();
            Set<Product> productCollection = new LinkedHashSet<>();
            CollectionManager collectionManager = new CollectionManager(productCollection);
            collectionManager.setProductDAO((ProductDAO) productDAO);
            collectionManager.setUserDAO((UserDAO) userDAO);
            collectionManager.setProductUserReferenceDAO((ProductUserReferenceDAO) productUserReferenceDAO);
            UserManager userManager = new UserManager();
            userManager.setUserDAO((UserDAO) userDAO);
            ServerAppContainer.getInstance().setCollectionManager(collectionManager);
            ServerAppContainer.getInstance().getCollectionManager().loadCollectionFromDB();
            ServerAppContainer.getInstance().setUserManager(userManager);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
