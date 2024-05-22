package org.example.contract.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInCommand implements Command{
    private String login;
    private String password;

}
