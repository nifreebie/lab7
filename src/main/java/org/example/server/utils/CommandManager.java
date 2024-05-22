package org.example.server.utils;

import org.example.contract.command.*;
import org.example.contract.responses.Response;
import org.example.server.command_handlers.*;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final Map<Class, Handler> commands;
    public CommandManager() {
        commands = new HashMap<>();
        commands.put(AddCommand.class, command -> new AddCommandHandler().handle((AddCommand) command));
        commands.put(HelpCommand.class, command -> new HelpCommandHandler().handle((HelpCommand) command));
        commands.put(RemoveByIdCommand.class, command -> new RemoveByIdCommandHandler().handle((RemoveByIdCommand) command));
        commands.put(ShowCommand.class, command -> new ShowCommandHandler().handle((ShowCommand) command));
        commands.put(InfoCommand.class, command -> new InfoCommandHandler().handle((InfoCommand) command));
        commands.put(ClearCommand.class, command -> new ClearCommandHandler().handle((ClearCommand) command));
        commands.put(UpdateCommand.class, command -> new UpdateCommandHandler().handle((UpdateCommand) command));
        commands.put(RemoveGreaterCommand.class, command -> new RemoveGreaterCommandHandler().handle((RemoveGreaterCommand) command));
        commands.put(RemoveLowerCommand.class, command -> new RemoveLowerCommandHandler().handle((RemoveLowerCommand) command));
        commands.put(AddIfMinCommand.class, command -> new AddIfMinCommandHandler().handle((AddIfMinCommand) command));
        commands.put(PrintDescendingCommand.class, command -> new PrintDescendingCommandHandler().handle((PrintDescendingCommand) command));
        commands.put(PrintFieldDescendingPartNumberCommand.class, command -> new PrintFieldDescendingPartNumberCommandHandler().handle((PrintFieldDescendingPartNumberCommand) command));
        commands.put(ExecuteScriptCommand.class, command -> new ExecuteScriptCommandHandler().handle((ExecuteScriptCommand) command));
        commands.put(SignInCommand.class, command -> new SignInCommandHandler().handle((SignInCommand) command));
        commands.put(SignUpCommand.class, command -> new SignUpCommandHandler().handle((SignUpCommand) command));


    }
    public Response executeCommand(Command command) {
        return commands.get(command.getClass()).apply(command);
    }
}
