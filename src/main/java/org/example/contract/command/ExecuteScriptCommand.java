package org.example.contract.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ExecuteScriptCommand implements Command{
    private List<Command> commandList;
    private String login;
    private String password;
}
