package org.example.server.command_handlers;

import org.example.contract.command.Command;
import org.example.contract.responses.Response;
import org.example.server.utils.ServerAppContainer;

abstract public class CommandHandler <T extends Command>{
    protected final ServerAppContainer app;

    public CommandHandler(){this.app = ServerAppContainer.getInstance();}

    public abstract Response handle(T command);
}
