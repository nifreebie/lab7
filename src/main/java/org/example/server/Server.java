package org.example.server;

import org.example.contract.model.Product;
import org.example.server.collection.CollectionManager;
import org.example.server.db.Database;
import org.example.server.db.DatabaseConnection;
import org.example.server.db.PostgresDatabase;
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
        Database db = new PostgresDatabase("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        try {
            DatabaseConnection connection = db.createConnection();
            Set<Product> productCollection = new LinkedHashSet<>();
            CollectionManager collectionManager = new CollectionManager(productCollection);
            collectionManager.setConnection(connection);
            UserManager userManager = new UserManager();
            userManager.setConnection(connection);
            ServerAppContainer.getInstance().setCollectionManager(collectionManager);
            ServerAppContainer.getInstance().getCollectionManager().loadCollectionFromDB();
            ServerAppContainer.getInstance().setUserManager(userManager);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
