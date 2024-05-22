package org.example.server.command_handlers;

import org.example.contract.command.HelpCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utils.StatusCode;

public class HelpCommandHandler extends CommandHandler<HelpCommand>{
    @Override
    public Response handle(HelpCommand command) {
        return new ResponseWithMessage(StatusCode._200_SUCCESS_, "");
    }
}
