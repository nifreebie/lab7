package org.example.server.utils;

import lombok.Getter;
import lombok.Setter;
import org.example.server.collection.CollectionManager;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
@Getter
@Setter
public class ServerAppContainer {
    private static ServerAppContainer instance;
    private CollectionManager collectionManager;
    private CommandManager commandManager;
    private UserManager userManager;

    private ServerAppContainer(){}

    public static ServerAppContainer getInstance(){
        if(instance == null){
            instance = new ServerAppContainer();
        }
        return instance;
    }
}
