package org.example.contract.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class ClearCommand implements Command{
    private String login;
    private String password;
}
