package org.example.contract.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpCommand implements Command {
    private String login;
    private String password;
}
