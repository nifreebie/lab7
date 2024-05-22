package org.example.server.command_handlers;

import org.example.contract.command.Command;
import org.example.contract.command.ExecuteScriptCommand;
import org.example.contract.responses.ExecuteScriptResponse;
import org.example.contract.responses.Response;
import org.example.contract.utils.StatusCode;

import java.util.ArrayList;
import java.util.List;

public class ExecuteScriptCommandHandler extends CommandHandler<ExecuteScriptCommand>{
    @Override
    public Response handle(ExecuteScriptCommand command) {
        List<Response> responseList = new ArrayList<>();
        for(Command c: command.getCommandList()){
            responseList.add(this.app.getCommandManager().executeCommand(c));
        }
        return new ExecuteScriptResponse(StatusCode._200_SUCCESS_,responseList);
    }
}
