package org.example.contract.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.model.User;

import java.util.List;

@AllArgsConstructor
@Getter
public class ExecuteScriptCommand implements Command{
    private List<Command> commandList;
    private User user;
}
